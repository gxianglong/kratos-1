package com.gxl.kratos.utils.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.gxl.kratos.utils.exception.UtilsException;

/**
 * 生成kratos的配置文件
 * 
 * @author gaoxianglong
 */
public class CreateXml {
	private String isShard;
	private String wr_weight;
	private String shardMode;
	private String dbRuleArray;
	private String tbRuleArray;
	private String dbSize;
	private String tbSize;
	private String user;
	private String password;
	private String jdbcUrl;
	private String driverClass;
	private String initialPoolSize;
	private String minPoolSize;
	private String maxPoolSize;
	private String maxStatements;
	private String maxIdleTime;
	private String consistent;
	private Marshaller marshaller;
	private boolean isShow;
	private int dataSourceIndex;

	public CreateXml() {
		try {
			JAXBContext jAXBContext = JAXBContext.newInstance(Beans.class);
			marshaller = jAXBContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成kratos的核心信息配置文件
	 * 
	 * @author gaoxianglong
	 * 
	 * @exception UtilsException
	 * 
	 * @return boolean 生成结果
	 */
	public boolean createCoreXml(File savePath) {
		boolean result = false;
		try {
			/* 创建配置文件的根目录<beans/>标签 */
			Beans beans = new Beans();
			/* 创建<bean/>子标签 */
			Bean bean1 = new Bean();
			bean1.setId("kJdbcTemplate");
			bean1.setClass_("com.gxl.kratos.jdbc.core.KratosJdbcTemplate");
			/* 创建<property/>子标签 */
			Property dataSource = new Property();
			dataSource.setName("dataSource");
			dataSource.setRef("kDataSourceGroup");
			Property wr_weight = new Property();
			wr_weight.setName("wr_weight");
			wr_weight.setValue(this.getWr_weight());
			Property shardMode = new Property();
			shardMode.setName("shardMode");
			shardMode.setValue(this.isShardMode());
			Property consistent = new Property();
			consistent.setName("consistent");
			consistent.setValue(this.getConsistent());
			Property dbRuleArray = new Property();
			dbRuleArray.setName("dbRuleArray");
			dbRuleArray.setValue(this.getDbRuleArray());
			Property tbRuleArray = new Property();
			tbRuleArray.setName("tbRuleArray");
			tbRuleArray.setValue(this.getTbRuleArray());
			List<Property> propertys = new ArrayList<Property>();
			propertys.add(dataSource);
			propertys.add(wr_weight);
			propertys.add(shardMode);
			propertys.add(consistent);
			propertys.add(dbRuleArray);
			propertys.add(tbRuleArray);
			bean1.setProperty(propertys);
			/* 创建<constructor_arg/>子标签 */
			ConstructorArg constructor_arg = new ConstructorArg();
			constructor_arg.setName("isShard");
			constructor_arg.setValue(this.isShard());
			bean1.setConstructor_arg(constructor_arg);
			/* 创建<bean/>子标签 */
			Bean bean2 = new Bean();
			bean2.setId("kDataSourceGroup");
			bean2.setClass_("com.gxl.kratos.jdbc.datasource.config.KratosDatasourceGroup");
			/* 创建<property/>子标签 */
			Property targetDataSources = new Property();
			targetDataSources.setName("targetDataSources");
			/* 创建<map/>子标签 */
			Map map = new Map();
			map.setKey_type("java.lang.Integer");
			List<Entry> entrys = new ArrayList<Entry>();
			for (int i = 0; i < Integer.parseInt(dbSize); i++) {
				/* 创建<entry/>子标签 */
				Entry entry = new Entry();
				entry.setKey(String.valueOf(i));
				entry.setValue_ref("dataSource" + (i + 1));
				entrys.add(entry);
			}
			map.setEntry(entrys);
			targetDataSources.setMap(map);
			propertys = new ArrayList<Property>();
			propertys.add(targetDataSources);
			bean2.setProperty(propertys);
			List<Bean> beanList = new ArrayList<Bean>();
			beanList.add(bean1);
			beanList.add(bean2);
			beans.setBean(beanList);
			if (this.getIsShow())
				marshaller.marshal(beans, System.out);
			marshaller.marshal(beans, savePath);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 生成kratos的数据源信息配置文件
	 * 
	 * @author gaoxianglong
	 * 
	 * @return boolean 生成结果
	 */
	public boolean createDatasourceXml(File savePath) {
		boolean result = false;
		try {
			/* 创建配置文件的根目录<beans/>标签 */
			Beans beans = new Beans();
			List<Bean> beanList = new ArrayList<Bean>();
			for (int i = 0; i < Integer.parseInt(dbSize); i++) {
				/* 创建<bean/>子标签 */
				Bean bean = new Bean();
				bean.setId("dataSource" + (this.getDataSourceIndex() + i));
				bean.setClass_("com.mchange.v2.c3p0.ComboPooledDataSource");
				List<Property> propertys = new ArrayList<Property>();
				Property user = new Property();
				user.setName("user");
				user.setValue(this.getUser());
				Property password = new Property();
				password.setName("password");
				password.setValue(this.getPassword());
				Property jdbcUrl = new Property();
				jdbcUrl.setName("jdbcUrl");
				if (i < 10) {
					jdbcUrl.setValue(this.getJdbcUrl() + "_000" + i);
				} else if (i < 100) {
					jdbcUrl.setValue(this.getJdbcUrl() + "_00" + i);
				} else if (i < 1000) {
					jdbcUrl.setValue(this.getJdbcUrl() + "_0" + i);
				} else {
					jdbcUrl.setValue(this.getJdbcUrl() + "_" + i);
				}
				Property driverClass = new Property();
				driverClass.setName("driverClass");
				driverClass.setValue(this.getDriverClass());
				Property initialPoolSize = new Property();
				initialPoolSize.setName("initialPoolSize");
				initialPoolSize.setValue(this.getInitialPoolSize());
				Property minPoolSize = new Property();
				minPoolSize.setName("minPoolSize");
				minPoolSize.setValue(this.getMinPoolSize());
				Property maxPoolSize = new Property();
				maxPoolSize.setName("maxPoolSize");
				maxPoolSize.setValue(this.getMaxPoolSize());
				Property maxStatements = new Property();
				maxStatements.setName("maxStatements");
				maxStatements.setValue(this.getMaxStatements());
				Property maxIdleTime = new Property();
				maxIdleTime.setName("maxIdleTime");
				maxIdleTime.setValue(this.getMaxIdleTime());
				propertys.add(user);
				propertys.add(password);
				propertys.add(jdbcUrl);
				propertys.add(driverClass);
				propertys.add(initialPoolSize);
				propertys.add(minPoolSize);
				propertys.add(maxPoolSize);
				propertys.add(maxStatements);
				propertys.add(maxIdleTime);
				bean.setProperty(propertys);
				beanList.add(bean);
			}
			beans.setBean(beanList);
			if (this.getIsShow())
				marshaller.marshal(beans, System.out);
			marshaller.marshal(beans, savePath);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(boolean isShow) {
		this.isShow = isShow;
	}

	public String isShard() {
		return isShard;
	}

	public void setShard(String isShard) {
		this.isShard = isShard;
	}

	public String getWr_weight() {
		return wr_weight;
	}

	public void setWr_weight(String wr_weight) {
		this.wr_weight = wr_weight;
	}

	public String isShardMode() {
		return shardMode;
	}

	public void setShardMode(String shardMode) {
		this.shardMode = shardMode;
	}

	public String getDbRuleArray() {
		return dbRuleArray;
	}

	public void setDbRuleArray(String dbRuleArray) {
		this.dbRuleArray = dbRuleArray;
	}

	public String getTbRuleArray() {
		return tbRuleArray;
	}

	public void setTbRuleArray(String tbRuleArray) {
		this.tbRuleArray = tbRuleArray;
	}

	public String getDbSize() {
		return dbSize;
	}

	public void setDbSize(String dbSize) {
		this.dbSize = dbSize;
	}

	public String getTbSize() {
		return tbSize;
	}

	public void setTbSize(String tbSize) {
		this.tbSize = tbSize;
	}

	public Marshaller getMarshaller() {
		return marshaller;
	}

	public void setMarshaller(Marshaller marshaller) {
		this.marshaller = marshaller;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getInitialPoolSize() {
		return initialPoolSize;
	}

	public void setInitialPoolSize(String initialPoolSize) {
		this.initialPoolSize = initialPoolSize;
	}

	public String getMinPoolSize() {
		return minPoolSize;
	}

	public void setMinPoolSize(String minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public String getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(String maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public String getMaxStatements() {
		return maxStatements;
	}

	public void setMaxStatements(String maxStatements) {
		this.maxStatements = maxStatements;
	}

	public String getMaxIdleTime() {
		return maxIdleTime;
	}

	public void setMaxIdleTime(String maxIdleTime) {
		this.maxIdleTime = maxIdleTime;
	}

	public int getDataSourceIndex() {
		return dataSourceIndex;
	}

	public void setDataSourceIndex(int dataSourceIndex) {
		this.dataSourceIndex = dataSourceIndex;
	}

	public String getConsistent() {
		return consistent;
	}

	public void setConsistent(String consistent) {
		this.consistent = consistent;
	}
}