package com.gxl.kratos.jdbc.mysql.sqlparser;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.gxl.kratos.jdbc.core.KratosJdbcTemplate;
import com.gxl.kratos.jdbc.exception.ShardException;
import com.gxl.kratos.jdbc.exception.SqlParserException;

/**
 * Sql解析器接口实现,目前仅支持一些较为简单的Sql语句,并且不支持表别名,如下所示:
 * 
 * *************************SELECT*****************************
 * 
 * 1、select * from tab where field1 = ?;
 * 
 * 2、select * from tab where field1 = ? and|or ...;
 * 
 * 3、select field2... from tab where field1 = ? and|or...;
 * 
 * ************************************************************
 * *************************INSERT*****************************
 * 
 * 1、insert into tab(field1) values(?);
 * 
 * 2、insert into tab(field1,field2...) values(?,?...);
 * 
 * ************************************************************
 * 
 * **************************DELETE****************************
 * 
 * 1、delete from tab where field1 = ?;
 * 
 * 2、delete from tab where field1 = ? and|or ...;
 * 
 * ************************************************************
 * 
 * **************************UPDATE****************************
 * 
 * 1、update tab set field2 = ? where field1 = ?;
 * 
 * 2、update tab set field2 = ?,... where field1 = ? and|or ...;
 * 
 * ************************************************************
 * 
 * @author gaoxianglong
 */
@Component("sqlParser")
public class KratosSqlParser implements SqlParser {
	private Logger logger = LoggerFactory.getLogger(KratosSqlParser.class);

	/**
	 * 封装java.lang.String的substring()字符串截取方法
	 * 
	 * @author gaoxianglong
	 * 
	 * @param str
	 *            目标字符串
	 * 
	 * @param begin
	 *            起始位置的字符串
	 * 
	 * @param end
	 *            结束为止的字符串
	 * 
	 * @throws SqlParserException
	 * 
	 * @return String 截取后的字符串信息
	 */
	public String substring(String str, String begin, String end) {
		String newStr = null;
		try {
			newStr = str.substring((str.indexOf(begin) + begin.length()), str.indexOf(end)).trim();
		} catch (Exception e) {
			throw new SqlParserException("kratos无法对当前Sql语句进行解析,Sql-->" + str);
		}
		return newStr;
	}

	@Override
	public String getTbName(String sql) {
		String tbName = null;
		/* 将sql语句中的单词全部转换为小写 */
		String sql_ = sql.toLowerCase();
		/* 获取sql语句的操作类型 */
		String operationType = sql_.split("\\s")[0];
		switch (operationType) {
		case Token.SELECT:
			tbName = substring(sql_, Token.FROM, Token.WHERE);
			break;
		case Token.INSERT:
			tbName = substring(sql_, Token.INTO, Token.LPAREN);
			break;
		case Token.UPDATE:
			tbName = substring(sql_, Token.UPDATE, Token.SET);
			break;
		case Token.DELETE:
			tbName = substring(sql_, Token.FROM, Token.WHERE);
		}
		logger.debug("sql-->" + sql_ + "\t中解析出来的数据库表名为-->" + tbName);
		return tbName;
	}

	@Override
	public long getKey(String sql, List<String> rules) {
		long key = -1;
		/* 将sql语句中的单词全部转换为小写 */
		String sql_ = sql.toLowerCase();
		/* 获取sql语句的操作类型 */
		String operationType = sql_.split("\\s")[0];
		if (operationType.equals(Token.INSERT)) {
			/* 解析所有的数据库参数字段 */
			String keys = substring(sql_, Token.LPAREN, Token.RPAREN);
			/* 检测是否包含多字段参数,比如intset into tab(field1,field2) value(...) */
			if (keys.indexOf(Token.COMMA) != -1) {
				/* 检测第一个字段是否是分库分表字段 */
				if (rules.contains(keys.split(Token.COMMA)[0])) {
					try {
						/* 获取values的所有参数 */
						String values = sql_.split(Token.VALUES)[1];
						/* 获取分库分表条件 */
						key = Long.parseLong(substring(values, Token.LPAREN, Token.RPAREN).split(Token.COMMA)[0]);
					} catch (Exception e) {
						throw new SqlParserException("kratos无法对当前Sql语句进行解析,Sql-->" + sql_);
					}
				} else {
					throw new ShardException("kratos无法找到分库分表条件,Sql-->" + sql_);
				}
			} else {
				/* 单字段参数,比如intset into tab(field1) value(...) */
				/* 检测字段是否是分库分表条件 */
				if (rules.contains(keys)) {
					try {
						/* 获取values的所有参数 */
						String values = sql_.split(Token.VALUES)[1];
						/* 获取分库分表条件 */
						key = Long.parseLong(substring(values, Token.LPAREN, Token.RPAREN).split(Token.COMMA)[0]);
					} catch (Exception e) {
						throw new SqlParserException("kratos无法对当前Sql语句进行解析,Sql-->" + sql_);
					}
				} else {
					throw new ShardException("kratos无法找到分库分表条件,Sql-->" + sql_);
				}
			}
		} else {
			/* 解析除了INSERT之外的Sql(SELECT、UPDATE、DELETE)语句 */
			String keys = substring(sql_, Token.WHERE, Token.EQ);
			/* 检测第一个字段是否是分库分表字段 */
			if (rules.contains(keys)) {
				try {
					/* 获取分库分表条件 */
					key = Long.parseLong(
							sql_.split(Token.WHERE)[1].split(Token.EQ)[1].split(Token.AND + Token.BAR + Token.OR)[0]
									.trim());
				} catch (Exception e) {
					throw new SqlParserException("kratos无法对当前Sql语句进行解析,Sql-->" + sql_);
				}
			} else {
				throw new ShardException("kratos无法找到分库分表条件,Sql-->" + sql_);
			}
		}
		logger.debug("sql-->" + sql_ + "\t解析出的sharding条件-->" + key);
		return key;
	}
}