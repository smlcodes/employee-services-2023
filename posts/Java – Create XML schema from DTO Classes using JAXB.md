JAXB provides a fast and convenient way to marshal (write) Java objects into XML and unmarshal (read) XML into objects. It supports a binding framework that maps XML elements and attributes to Java fields and properties using Java annotations.

Maven Dependencies
==================
```
<dependency>
   <groupId>javax.xml.bind</groupId>
   <artifactId>jaxb-api</artifactId>
   <version>2.3.1</version>
 </dependency>
 <dependency>
   <groupId>com.sun.xml.bind</groupId>
   <artifactId>jaxb-core</artifactId>
   <version>2.3.0.1</version>
 </dependency>
 <dependency>
   <groupId>com.sun.xml.bind</groupId>
   <artifactId>jaxb-impl</artifactId>
   <version>2.3.3</version>
 </dependency>
```

JAXB Annotations
================

JAXB uses Java annotations to augment the generated classes with additional information. Adding such annotations to existing Java classes prepares them for the JAXB runtime.

Let's take our Employee DTO and understand the JAXB annotations

```
@XmlRootElement
 @XmlAccessorType(XmlAccessType.FIELD)
 public class EmployeeDto {

 private Long id;

 @XmlElement(required = true)
 private String name;

 @XmlElement(name = "salary", required = true)
 private Double salary;

 @XmlElement
 private String city;

 @XmlElement(required = true)
 private AccountDto account;

 @XmlElement
 private List<DocumentDto> documentList;

 private String createdBy;
 private String modifiedBy;

 private Date createdDate;
 private Date modifiedDate;

 }
```

**@XmlRootElement**. This annotation should be applied to the class that you want to be treated as the root element when converting it to XML.

**@XmlAccessorType(XmlAccessType.FIELD)** to specify that JAXB should use field access for XML binding. This means that fields (instance variables) are directly used for reading and writing XML, rather than getter and setter methods

**@XmlType**: define the order in which the fields are written in the XML file

**@XmlTransient** annotation in JAXB is used to mark a class, field, or property as transient, indicating that it should be excluded from XML serialization and deserialization.

**@XmlElement** is an annotation used to indicate that the annotated field or property should be mapped to an XML element.

-   **name="salary "** specifies the name of the XML element to which the field or property should be mapped. In this case, it's "**salary**."
-   **required=true** indicates that the XML element is required in the XML document. If **required** is set to **true**, it means that the XML element must be present when marshalling (converting Java objects to XML) and unmarshalling (converting XML to Java objects). If the element is missing, it may result in an error.

JAXB-2 Maven Plugin
===================

This plugin uses the Java API for XML Binding (JAXB), version 2+, to generate Java classes from XML Schemas (and optionally binding files) or to create XML schema from an annotated Java class

Update maven Build plugin

```
<build>
   <plugins>

      <plugin>
         <groupId>org.codehaus.mojo</groupId>
         <artifactId>jaxb2-maven-plugin</artifactId>
         <version>2.5.0</version>
         <executions>
            <execution>
               <id>schemagen</id>
               <goals>
                  <goal>schemagen</goal>
               </goals>
            </execution>
         </executions>
         <configuration>
            <sources>
               <!-- Update XML DTOs location here -->
               <source>src/main/java/com/employee/api/v1/model/dto</source>
            </sources>
         </configuration>
      </plugin>

   </plugins>
</build>
```

in source tag, we need to update Dto class location**

At last, do **mvn clean install**

Now, we can see the generated schema document inside **target/schemagen-work**

![image](https://github.com/smlcodes/employee-services-2023/assets/20472904/67d1f79a-2c79-4869-b964-217d595a35f3)


That's all on a High level.
