<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}">
    <#list methods as method>
        <#if method??>
            <#switch method.type>
                <#case "insert">

    <insert id="${method.id}">
        INSERT INTO ${method.tableName} (
            <#list method.fields as field>
                <#if field_index < method.fields?size - 1>
            ${field},
                <#else >
            ${field}
                </#if>
            </#list>
        ) VALUES (
            <#list method.smallHumpFields as smallHumpFields>
                <#if smallHumpFields_index < method.smallHumpFields?size - 1>
            ${r'#{'}${smallHumpFields}${'}'},
                <#else >
            ${r'#{'}${smallHumpFields}${'}'}
                </#if>
            </#list>
        )
    </insert>
                <#break>
                <#case "update">

    <update id="${method.id}">
        UPDATE ${method.tableName} SET
        <#list method.fields as field>
            <#if field_index < method.fields?size - 1>
            ${field} = ${r'#{'}${method.smallHumpFields[field_index]}${'}'},
            <#else>
            ${field} = ${r'#{'}${method.smallHumpFields[field_index]}${'}'}
            </#if>
        </#list>
        <#if method.conditions??>
        <where>
            <#list method.conditions as condition>
                <#if condition_index < method.conditions?size - 1>
            ${condition} = ${r'#{'}${method.smallHumpConditions[condition_index]}${r'}'},
                <#else>
            ${condition} = ${r'#{'}${method.smallHumpConditions[condition_index]}${r'}'}
                </#if>
            </#list>
        </where>
        </#if>
    </update>
                <#break>
                <#case "select">

    <select id="${method.id}" resultType="${method.resultType}">
        SELECT
        <#list method.fields as field>
            <#if field_index < method.fields?size - 1>
            ${field},
            <#else>
            ${field}
            </#if>
        </#list>
        FROM ${method.tableName}
        <#if method.conditions??>
        <where>
        <#list method.conditions as condition>
            <#if condition_index < method.conditions?size - 1>
            ${condition} = ${r'#{'}${method.smallHumpConditions[condition_index]}${r'}'},
            <#else>
            ${condition} = ${r'#{'}${method.smallHumpConditions[condition_index]}${r'}'}
            </#if>
        </#list>
        </where>
        </#if>
    </select>
                <#break>
                <#case "delete">

    <delete id="${method.id}">
        DELETE FROM ${method.tableName}
        <#if method.conditions??>
        <where>
            <#list method.conditions as condition>
                <#if condition_index < method.conditions?size - 1>
            ${condition} = ${r'#{'}${method.smallHumpConditions[condition_index]}${r'}'},
                <#else>
            ${condition} = ${r'#{'}${method.smallHumpConditions[condition_index]}${r'}'}
                </#if>
            </#list>
        </where>
        </#if>
    </delete>
                <#break>
            </#switch>
        </#if>
    </#list>
</mapper>