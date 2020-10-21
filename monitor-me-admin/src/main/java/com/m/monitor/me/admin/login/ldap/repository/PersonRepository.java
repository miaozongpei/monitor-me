package com.m.monitor.me.admin.login.ldap.repository;
 
import com.m.monitor.me.admin.login.ldap.entity.Person;
import org.springframework.data.repository.CrudRepository;
 
import javax.naming.Name;
 
public interface PersonRepository extends CrudRepository<Person, Name> {
}