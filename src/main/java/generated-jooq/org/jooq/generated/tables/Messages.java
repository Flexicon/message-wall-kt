/*
 * This file is generated by jOOQ.
 */
package org.jooq.generated.tables;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.generated.Keys;
import org.jooq.generated.Public;
import org.jooq.generated.tables.records.MessagesRecord;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.14.8",
        "schema version:0001"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Messages extends TableImpl<MessagesRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.messages</code>
     */
    public static final Messages MESSAGES = new Messages();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MessagesRecord> getRecordType() {
        return MessagesRecord.class;
    }

    /**
     * The column <code>public.messages.id</code>.
     */
    public final TableField<MessagesRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.messages.text</code>.
     */
    public final TableField<MessagesRecord, String> TEXT = createField(DSL.name("text"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>public.messages.author</code>.
     */
    public final TableField<MessagesRecord, String> AUTHOR = createField(DSL.name("author"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>public.messages.created_at</code>.
     */
    public final TableField<MessagesRecord, LocalDateTime> CREATED_AT = createField(DSL.name("created_at"), SQLDataType.LOCALDATETIME(6).nullable(false), this, "");

    private Messages(Name alias, Table<MessagesRecord> aliased) {
        this(alias, aliased, null);
    }

    private Messages(Name alias, Table<MessagesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.messages</code> table reference
     */
    public Messages(String alias) {
        this(DSL.name(alias), MESSAGES);
    }

    /**
     * Create an aliased <code>public.messages</code> table reference
     */
    public Messages(Name alias) {
        this(alias, MESSAGES);
    }

    /**
     * Create a <code>public.messages</code> table reference
     */
    public Messages() {
        this(DSL.name("messages"), null);
    }

    public <O extends Record> Messages(Table<O> child, ForeignKey<O, MessagesRecord> key) {
        super(child, key, MESSAGES);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public Identity<MessagesRecord, Integer> getIdentity() {
        return (Identity<MessagesRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<MessagesRecord> getPrimaryKey() {
        return Keys.MESSAGES_PKEY;
    }

    @Override
    public List<UniqueKey<MessagesRecord>> getKeys() {
        return Arrays.<UniqueKey<MessagesRecord>>asList(Keys.MESSAGES_PKEY);
    }

    @Override
    public Messages as(String alias) {
        return new Messages(DSL.name(alias), this);
    }

    @Override
    public Messages as(Name alias) {
        return new Messages(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Messages rename(String name) {
        return new Messages(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Messages rename(Name name) {
        return new Messages(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, String, String, LocalDateTime> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
