/**
 * This class is generated by jOOQ
 */
package com.jooqtest.model.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Car extends org.jooq.impl.TableImpl<com.jooqtest.model.tables.records.CarRecord> {

	private static final long serialVersionUID = 612421842;

	/**
	 * The singleton instance of <code>jooqtest.car</code>
	 */
	public static final com.jooqtest.model.tables.Car CAR = new com.jooqtest.model.tables.Car();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<com.jooqtest.model.tables.records.CarRecord> getRecordType() {
		return com.jooqtest.model.tables.records.CarRecord.class;
	}

	/**
	 * The column <code>jooqtest.car.id</code>.
	 */
	public final org.jooq.TableField<com.jooqtest.model.tables.records.CarRecord, java.lang.Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

	/**
	 * The column <code>jooqtest.car.manufactor</code>.
	 */
	public final org.jooq.TableField<com.jooqtest.model.tables.records.CarRecord, java.lang.String> MANUFACTOR = createField("manufactor", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * The column <code>jooqtest.car.create_date</code>.
	 */
	public final org.jooq.TableField<com.jooqtest.model.tables.records.CarRecord, java.sql.Date> CREATE_DATE = createField("create_date", org.jooq.impl.SQLDataType.DATE, this, "");

	/**
	 * The column <code>jooqtest.car.model</code>.
	 */
	public final org.jooq.TableField<com.jooqtest.model.tables.records.CarRecord, java.lang.String> MODEL = createField("model", org.jooq.impl.SQLDataType.VARCHAR.length(30), this, "");

	/**
	 * The column <code>jooqtest.car.color</code>.
	 */
	public final org.jooq.TableField<com.jooqtest.model.tables.records.CarRecord, java.lang.String> COLOR = createField("color", org.jooq.impl.SQLDataType.VARCHAR.length(20), this, "");

	/**
	 * The column <code>jooqtest.car.door_quantity</code>.
	 */
	public final org.jooq.TableField<com.jooqtest.model.tables.records.CarRecord, java.lang.Integer> DOOR_QUANTITY = createField("door_quantity", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>jooqtest.car.patrol</code>.
	 */
	public final org.jooq.TableField<com.jooqtest.model.tables.records.CarRecord, java.lang.Byte> PATROL = createField("patrol", org.jooq.impl.SQLDataType.TINYINT, this, "");

	/**
	 * Create a <code>jooqtest.car</code> table reference
	 */
	public Car() {
		this("car", null);
	}

	/**
	 * Create an aliased <code>jooqtest.car</code> table reference
	 */
	public Car(java.lang.String alias) {
		this(alias, com.jooqtest.model.tables.Car.CAR);
	}

	private Car(java.lang.String alias, org.jooq.Table<com.jooqtest.model.tables.records.CarRecord> aliased) {
		this(alias, aliased, null);
	}

	private Car(java.lang.String alias, org.jooq.Table<com.jooqtest.model.tables.records.CarRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, com.jooqtest.model.Jooqtest.JOOQTEST, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Identity<com.jooqtest.model.tables.records.CarRecord, java.lang.Long> getIdentity() {
		return com.jooqtest.model.Keys.IDENTITY_CAR;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<com.jooqtest.model.tables.records.CarRecord> getPrimaryKey() {
		return com.jooqtest.model.Keys.KEY_CAR_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<com.jooqtest.model.tables.records.CarRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<com.jooqtest.model.tables.records.CarRecord>>asList(com.jooqtest.model.Keys.KEY_CAR_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.jooqtest.model.tables.Car as(java.lang.String alias) {
		return new com.jooqtest.model.tables.Car(alias, this);
	}

	/**
	 * Rename this table
	 */
	public com.jooqtest.model.tables.Car rename(java.lang.String name) {
		return new com.jooqtest.model.tables.Car(name, null);
	}
}
