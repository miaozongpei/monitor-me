package com.m.monitor.me.admin.auth.login.ldap.entity;
 
import lombok.Data;
import org.springframework.ldap.odm.annotations.*;
 
import javax.naming.Name;
 
@Entry(base = "dc=maxcrc,dc=com", objectClasses = "inetOrgPerson")
@Data
public class Person {
    @Id
    private Name id;
    @DnAttribute(value = "uid")
    private String uid;
    @Attribute(name = "cn")
    private String commonName;
    @Attribute(name = "sn")
    private String suerName;
    private String userPassword;
 
}