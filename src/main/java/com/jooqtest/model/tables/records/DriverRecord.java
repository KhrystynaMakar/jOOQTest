/**
 * This class is generated by jOOQ
 */
package com.jooqtest.model.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DriverRecord extends org.jooq.impl.UpdatableRecordImpl<com.jooqtest.model.tables.records.DriverRecord> implements org.jooq.Record7<java.lang.Long, java.lang.String, java.lang.String, java.sql.Date, java.lang.String, java.lang.Long, java.lang.Long> {

	private static final long serialVersionUID = 542028347;

	/**
	 * Setter for <code>jooqtest.driver.id</code>.
	 */
	public void setId(java.lang.Long value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>jooqtest.driver.id</code>.
	 */
	public java.lang.Long getId() {
		return (java.lang.Long) getValue(0);
	}

	/**
	 * Setter for <code>jooqtest.driver.first_name</code>.
	 */
	public void setFirstName(java.lang.String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>jooqtest.driver.first_name</code>.
	 */
	public java.lang.String getFirstName() {
		return (java.lang.String) getValue(1);
	}

	/**
	 * Setter for <code>jooqtest.driver.last_name</code>.
	 */
	public void setLastName(java.lang.String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>jooqtest.driver.last_name</code>.
	 */
	public java.lang.String getLastName() {
		return (java.lang.String) getValue(2);
	}

	/**
	 * Setter for <code>jooqtest.driver.birthday</code>.
	 */
	public void setBirthday(java.sql.Date value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>jooqtest.driver.birthday</code>.
	 */
	public java.sql.Date getBirthday() {
		return (java.sql.Date) getValue(3);
	}

	/**
	 * Setter for <code>jooqtest.driver.telephone</code>.
	 */
	public void setTelephone(java.lang.String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>jooqtest.driver.telephone</code>.
	 */
	public java.lang.String getTelephone() {
		return (java.lang.String) getValue(4);
	}

	/**
	 * Setter for <code>jooqtest.driver.car_id</code>.
	 */
	public void setCarId(java.lang.Long value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>jooqtest.driver.car_id</code>.
	 */
	public java.lang.Long getCarId() {
		return (java.lang.Long) getValue(5);
	}

	/**
	 * Setter for <code>jooqtest.driver.company_id</code>.
	 */
	public void setCompanyId(java.lang.Long value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>jooqtest.driver.company_id</code>.
	 */
	public java.lang.Long getCompanyId() {
		return (java.lang.Long) getValue(6);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.Long> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record7 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row7<java.lang.Long, java.lang.String, java.lang.String, java.sql.Date, java.lang.String, java.lang.Long, java.lang.Long> fieldsRow() {
		return (org.jooq.Row7) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row7<java.lang.Long, java.lang.String, java.lang.String, java.sql.Date, java.lang.String, java.lang.Long, java.lang.Long> valuesRow() {
		return (org.jooq.Row7) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field1() {
		return com.jooqtest.model.tables.Driver.DRIVER.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return com.jooqtest.model.tables.Driver.DRIVER.FIRST_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field3() {
		return com.jooqtest.model.tables.Driver.DRIVER.LAST_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.sql.Date> field4() {
		return com.jooqtest.model.tables.Driver.DRIVER.BIRTHDAY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field5() {
		return com.jooqtest.model.tables.Driver.DRIVER.TELEPHONE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field6() {
		return com.jooqtest.model.tables.Driver.DRIVER.CAR_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field7() {
		return com.jooqtest.model.tables.Driver.DRIVER.COMPANY_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getFirstName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value3() {
		return getLastName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.sql.Date value4() {
		return getBirthday();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value5() {
		return getTelephone();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value6() {
		return getCarId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value7() {
		return getCompanyId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DriverRecord value1(java.lang.Long value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DriverRecord value2(java.lang.String value) {
		setFirstName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DriverRecord value3(java.lang.String value) {
		setLastName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DriverRecord value4(java.sql.Date value) {
		setBirthday(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DriverRecord value5(java.lang.String value) {
		setTelephone(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DriverRecord value6(java.lang.Long value) {
		setCarId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DriverRecord value7(java.lang.Long value) {
		setCompanyId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DriverRecord values(java.lang.Long value1, java.lang.String value2, java.lang.String value3, java.sql.Date value4, java.lang.String value5, java.lang.Long value6, java.lang.Long value7) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached DriverRecord
	 */
	public DriverRecord() {
		super(com.jooqtest.model.tables.Driver.DRIVER);
	}

	/**
	 * Create a detached, initialised DriverRecord
	 */
	public DriverRecord(java.lang.Long id, java.lang.String firstName, java.lang.String lastName, java.sql.Date birthday, java.lang.String telephone, java.lang.Long carId, java.lang.Long companyId) {
		super(com.jooqtest.model.tables.Driver.DRIVER);

		setValue(0, id);
		setValue(1, firstName);
		setValue(2, lastName);
		setValue(3, birthday);
		setValue(4, telephone);
		setValue(5, carId);
		setValue(6, companyId);
	}
}
