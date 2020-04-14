<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${rootPath}.mapper.${objectName}Mapper">

	<!-- 列表(分页) -->
	<select id="list" resultType="${rootPath}.model.${objectName}" parameterType="${rootPath}.model.${objectName}">
		select
		<#list fieldList as var>
			<#if var_has_next>
				<#if var[1] == 'Date'>
					DATE_FORMAT( a.${var[0]},'%Y-%m-%d %H:%i:%s')  ${var[0]},
				<#else>
					a.${var[0]},
				</#if>
			<#else>
				<#if var[1] == 'Date'>
					DATE_FORMAT( a.${var[0]},'%Y-%m-%d %H:%i:%s')  ${var[0]}
				<#else>
					a.${var[0]}
				</#if>
			</#if>
		</#list>
		from
		${tablename} a
		<where>
			<#list fieldList as var>
				<#if var_index == 1>
			<if test="${var[0]} != null and ${var[0]} !=''">
				and  a.${var[0]} like CONCAT('%', ${r"#{"}${var[0]}${r"}"},'%')
			</if>
				</#if>
			</#list>
		</where>
	</select>

	<!-- 通过ID获取数据 -->
	<#if keyType == 'Integer'>
	<select id="getById" resultType="${rootPath}.model.${objectName}" parameterType="Integer">
	<#else>
	<select id="getById" resultType="${rootPath}.model.${objectName}" parameterType="String">
	</#if>
		select
		<#list fieldList as var>
			<#if var_has_next>
				a.${var[0]},
			<#else>
				a.${var[0]}
			</#if>
		</#list>
		from  ${tablename} a where id = ${r"#{"}id${r"}"} limit 1
	</select>
	
	<!-- 新增-->
	<insert id="insert" parameterType="${rootPath}.model.${objectName}">
		insert into ${tablename}(
	<#list fieldList as var>
		<#if var_has_next>
			${var[0]},	
		<#else>
		    ${var[0]}
		</#if>
	</#list>
		) values (
	<#list fieldList as var>
		<#if var_has_next>
			${r"#{"}${var[5]}${r"}"},
		<#else>
		    ${r"#{"}${var[5]}${r"}"}
		</#if>			
	</#list>
		)
	</insert>
	
	
	<!-- 修改 -->
	<update id="update" parameterType="${rootPath}.model.${objectName}">
		update  ${tablename}
		    <set>
		<#list fieldList as var>
			<#if var[3] == "是">
				<if test="${var[5]} != null and ${var[5]} != '' ">
					${var[0]} = ${r"#{"}${var[5]}${r"}"},
				</if>
			</#if>
		</#list>
			</set>
		where  id = ${r"#{"}id${r"}"}
	</update>


	<!--批量删除-->
	<delete id="deletebth">
		delete from ${tablename} where  id in
		<foreach collection="array" item="item" open="(" close=")" separator=",">
			${r"#{item}"}
		</foreach>
	</delete>
	

	

</mapper>