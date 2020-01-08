package com.MDMREST.entity.search;

import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.MDMREST.util.DateFunction;

public class SearchObject {
	
	private static final Logger log = LoggerFactory.getLogger(SearchObject.class);

	public String column = ""; 		// name of column in database
	public String operator = ""; 	// like or equal or in
	public List<String> values; 	// like or equal ~ single value in ~ multi values
		
	public Predicate BuildCondition(Root<?> root, CriteriaBuilder cb) {
		String parent = "";
		String child = "";
		
		if (column.indexOf(".") > -1) {
			parent = column.substring(0, column.indexOf("."));
			child = column.substring(column.indexOf(".") + 1);
		
		
			if (operator.equalsIgnoreCase("like")) {
				if(root.get(parent).get(child).getJavaType() == Double.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Double value: %s", this.operator, this.column, values.get(0)));
					return cb.equal(root.get(parent).get(child).as(Double.class), Double.parseDouble(values.get(0)));
				}
				else
				if(root.get(parent).get(child).getJavaType()== Integer.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Integer value: %s", this.operator, this.column, values.get(0)));
					return cb.equal(root.get(parent).get(child).as(Integer.class), Integer.parseInt(values.get(0)));
				}
				else
				if(root.get(parent).get(child).getJavaType()== Date.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Date (format input:MM/dd/yyyy HH:mm:ss) value: %s", this.operator, this.column, values.get(0)));
					return cb.equal(root.get(parent).get(child).as(Date.class), DateFunction.StringToDate(values.get(0), DateFunction.MMddyyyyHHmmss));
				}
				else
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type String value: %s", this.operator, this.column, values.get(0)));
					return cb.like(cb.trim(cb.lower(root.get(parent).get(child))), "%" + values.get(0).trim().toLowerCase() + "%");
				}				
				
			} else if (operator.equalsIgnoreCase("notlike")) {
				if(root.get(parent).get(child).getJavaType() == Double.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Double value: %s", this.operator, this.column, values.get(0)));
					return cb.notEqual(root.get(parent).get(child).as(Double.class), Double.parseDouble(values.get(0)));
				}
				else
				if(root.get(parent).get(child).getJavaType()== Integer.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Integer value: %s", this.operator, this.column, values.get(0)));
					return cb.notEqual(root.get(parent).get(child).as(Integer.class), Integer.parseInt(values.get(0)));
				}
				else
				if(root.get(parent).get(child).getJavaType()== Date.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Date (format input:MM/dd/yyyy HH:mm:ss) value: %s", this.operator, this.column, values.get(0)));
					return cb.notEqual(root.get(parent).get(child).as(Date.class), DateFunction.StringToDate(values.get(0), DateFunction.MMddyyyyHHmmss));
				}
				else
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type String value: %s", this.operator, this.column, values.get(0)));
					return cb.notLike(cb.trim(cb.lower(root.get(parent).get(child))), "%" + values.get(0).trim().toLowerCase() + "%");
				}
				
			} else if (operator.equalsIgnoreCase("equal") || operator.equalsIgnoreCase("=")) {
				
				if(root.get(parent).get(child).getJavaType() == Double.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Double value: %s", this.operator, this.column, values.get(0)));
					return cb.equal(root.get(parent).get(child).as(Double.class), Double.parseDouble(values.get(0)));					
				}
				else
				if(root.get(parent).get(child).getJavaType() == Integer.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Integer value: %s", this.operator, this.column, values.get(0)));
					return cb.equal(root.get(parent).get(child).as(Integer.class), Integer.parseInt(values.get(0)));					
				}
				else
				if(root.get(parent).get(child).getJavaType() == Boolean.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Boolean value: %s", this.operator, this.column, values.get(0)));
					return cb.equal(root.get(parent).get(child).as(Boolean.class), values.get(0).matches("1|true|True")? true : false );					
				}
				else
				if(root.get(parent).get(child).getJavaType()== Date.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Date (format input:MM/dd/yyyy HH:mm:ss) value: %s", this.operator, this.column, values.get(0)));
					return cb.equal(root.get(parent).get(child).as(Date.class), DateFunction.StringToDate(values.get(0), DateFunction.MMddyyyyHHmmss));
				}
				else
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type String value: %s", this.operator, this.column, values.get(0)));
					return cb.equal(cb.trim(cb.lower(root.get(parent).get(child))), values.get(0).trim().toLowerCase());
				}
			
			} else if (operator.equalsIgnoreCase("notequal") || operator.equalsIgnoreCase("<>")) {
				if(root.get(parent).get(child).getJavaType() == Double.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Double value: %s", this.operator, this.column, values.get(0)));
					return cb.notEqual(root.get(parent).get(child).as(Double.class), Double.parseDouble(values.get(0)));					
				}
				else
				if(root.get(parent).get(child).getJavaType() == Integer.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Integer value: %s", this.operator, this.column, values.get(0)));
					return cb.notEqual(root.get(parent).get(child).as(Integer.class), Integer.parseInt(values.get(0)));					
				}
				else
				if(root.get(parent).get(child).getJavaType() == Boolean.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Boolean value: %s", this.operator, this.column, values.get(0)));
					return cb.notEqual(root.get(parent).get(child).as(Boolean.class), values.get(0).matches("1|true|True")? true : false );					
				}
				else
				if(root.get(parent).get(child).getJavaType()== Date.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Date (format input:MM/dd/yyyy HH:mm:ss) value: %s", this.operator, this.column, values.get(0)));
					return cb.notEqual(root.get(parent).get(child).as(Date.class), DateFunction.StringToDate(values.get(0), DateFunction.MMddyyyyHHmmss));
				}
				else
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type String value: %s", this.operator, this.column, values.get(0)));
					return cb.notEqual(cb.trim(cb.lower(root.get(parent).get(child))), values.get(0).trim().toLowerCase());
				}
				
			} else if (operator.equalsIgnoreCase("in")) {
				
				if(root.get(parent).get(child).getJavaType() == Integer.class || root.get(parent).get(child).getJavaType() == Double.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Integer array or Double array values: %s", this.operator, this.column,  String.join("|", values)));
					return root.get(parent).get(child).in(values);
				}
				
				log.info(String.format("Query operation \"%s\" on column \"%s\" type String array values: %s", this.operator, this.column,  String.join("|", values)));
				ListIterator<String> iterator = values.listIterator();
				while (iterator.hasNext()) {
					iterator.set(iterator.next().toString().trim().toLowerCase());
				}
				return cb.trim(cb.lower(root.get(parent).get(child))).in(values);
				
			} else if (operator.equalsIgnoreCase("between")) {
				if (root.get(parent).get(child).getJavaType() == Double.class) {
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Double values: %s ", this.operator, this.column,  String.join("|", values)));
					return cb.between(root.get(parent).get(child).as(Double.class), Double.parseDouble(values.get(0)), Double.parseDouble(values.get(1)));
					
				}
				else
				if (root.get(parent).get(child).getJavaType() == Integer.class) {
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Integer values: %s ", this.operator, this.column,  String.join("|", values)));
					return cb.between(root.get(parent).get(child).as(Integer.class), Integer.parseInt(values.get(0)), Integer.parseInt(values.get(1)));
					
				} else if (root.get(parent).get(child).getJavaType() == Date.class) {
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Date (format input:MM/dd/yyyy HH:mm:ss) values: %s ", this.operator, this.column,  String.join("|", values)));
					return cb.between(root.get(parent).get(child).as(Date.class),
							DateFunction.StringToDate(values.get(0), DateFunction.MMddyyyyHHmmss),
							DateFunction.StringToDate(values.get(1), DateFunction.MMddyyyyHHmmss));
				}
			
			} else if (operator.equalsIgnoreCase("greaterthan") || operator.equalsIgnoreCase(">")) {
				if(root.get(parent).get(child).getJavaType() == Double.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Double value: %s", this.operator, this.column, values.get(0)));
					return cb.gt(root.get(parent).get(child).as(Double.class), Double.parseDouble(values.get(0)));					
				}
				else
				if(root.get(parent).get(child).getJavaType() == Integer.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Integer value: %s", this.operator, this.column, values.get(0)));
					return cb.gt(root.get(parent).get(child).as(Integer.class), Integer.parseInt(values.get(0)));					
				}
				else
				if(root.get(parent).get(child).getJavaType()== Date.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Date (format input:MM/dd/yyyy HH:mm:ss) value: %s", this.operator, this.column, values.get(0)));
					return cb.greaterThan(root.get(parent).get(child).as(Date.class), DateFunction.StringToDate(values.get(0), DateFunction.MMddyyyyHHmmss));
				}
				else
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type String value: %s", this.operator, this.column, values.get(0)));
					return cb.greaterThan(cb.trim(cb.lower(root.get(parent).get(child))), values.get(0).trim().toLowerCase());
				}
				
			} else if (operator.equalsIgnoreCase("greaterthanorequal") || operator.equalsIgnoreCase(">=")) {
				if(root.get(parent).get(child).getJavaType() == Double.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Double value: %s", this.operator, this.column, values.get(0)));
					return cb.ge(root.get(parent).get(child).as(Double.class), Double.parseDouble(values.get(0)));					
				}
				else
				if(root.get(parent).get(child).getJavaType() == Integer.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Integer value: %s", this.operator, this.column, values.get(0)));
					return cb.ge(root.get(parent).get(child).as(Integer.class), Integer.parseInt(values.get(0)));					
				}
				else
				if(root.get(parent).get(child).getJavaType()== Date.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Date (format input:MM/dd/yyyy HH:mm:ss) value: %s", this.operator, this.column, values.get(0)));
					return cb.greaterThanOrEqualTo(root.get(parent).get(child).as(Date.class), DateFunction.StringToDate(values.get(0), DateFunction.MMddyyyyHHmmss));
				}
				else
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type String value: %s", this.operator, this.column, values.get(0)));
					return cb.greaterThanOrEqualTo(cb.trim(cb.lower(root.get(parent).get(child))), values.get(0).trim().toLowerCase());
				}
				
			} else if (operator.equalsIgnoreCase("lessthan") || operator.equalsIgnoreCase("<")) {
				if(root.get(parent).get(child).getJavaType() == Double.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Double value: %s", this.operator, this.column, values.get(0)));
					return cb.lt(root.get(parent).get(child).as(Double.class), Double.parseDouble(values.get(0)));					
				}
				else
				if(root.get(parent).get(child).getJavaType() == Integer.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Integer value: %s", this.operator, this.column, values.get(0)));
					return cb.lt(root.get(parent).get(child).as(Integer.class), Integer.parseInt(values.get(0)));					
				}
				else
				if(root.get(parent).get(child).getJavaType()== Date.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Date (format input:MM/dd/yyyy HH:mm:ss) value: %s", this.operator, this.column, values.get(0)));
					return cb.lessThan(root.get(parent).get(child).as(Date.class), DateFunction.StringToDate(values.get(0), DateFunction.MMddyyyyHHmmss));
				}
				else
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type String value: %s", this.operator, this.column, values.get(0)));
					return cb.lessThan(cb.trim(cb.lower(root.get(parent).get(child))), values.get(0).trim().toLowerCase());
				}
				
			} else if (operator.equalsIgnoreCase("lessthanorequal") || operator.equalsIgnoreCase("<=")) {
				if(root.get(parent).get(child).getJavaType() == Double.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Double value: %s", this.operator, this.column, values.get(0)));
					return cb.le(root.get(parent).get(child).as(Double.class), Double.parseDouble(values.get(0)));					
				}
				else
				if(root.get(parent).get(child).getJavaType() == Integer.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Integer value: %s", this.operator, this.column, values.get(0)));
					return cb.le(root.get(parent).get(child).as(Integer.class), Integer.parseInt(values.get(0)));					
				}
				else
				if(root.get(parent).get(child).getJavaType()== Date.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Date (format input:MM/dd/yyyy HH:mm:ss) value: %s", this.operator, this.column, values.get(0)));
					return cb.lessThanOrEqualTo(root.get(parent).get(child).as(Date.class), DateFunction.StringToDate(values.get(0), DateFunction.MMddyyyyHHmmss));
				}
				else
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type String value: %s", this.operator, this.column, values.get(0)));
					return cb.lessThanOrEqualTo(cb.trim(cb.lower(root.get(parent).get(child))), values.get(0).trim().toLowerCase());
				}
			
			}
		} else {
						
			if (operator.equalsIgnoreCase("like")) {
				if(root.get(column).getJavaType() == Double.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Double value: %s", this.operator, this.column, values.get(0)));
					return cb.equal(root.get(column).as(Double.class), Double.parseDouble(values.get(0)));
				}
				else 
				if(root.get(column).getJavaType() == Integer.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Integer value: %s", this.operator, this.column, values.get(0)));
					return cb.equal(root.get(column).as(Integer.class), Integer.parseInt(values.get(0)));
				}
				else
				if(root.get(column).getJavaType()== Date.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Date (format input:MM/dd/yyyy HH:mm:ss) value: %s", this.operator, this.column, values.get(0)));
					return cb.equal(root.get(column).as(Date.class), DateFunction.StringToDate(values.get(0), DateFunction.MMddyyyyHHmmss));
				}
				else
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type String value: %s", this.operator, this.column, values.get(0)));
					return cb.like(cb.trim(cb.lower(root.get(column))), "%" + values.get(0).trim().toLowerCase() + "%");
				}
				
			} else if (operator.equalsIgnoreCase("notlike")) {
				if(root.get(column).getJavaType() == Double.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Double value: %s", this.operator, this.column, values.get(0)));
					return cb.notEqual(root.get(column).as(Double.class), Double.parseDouble(values.get(0)));
				}
				else 
				if(root.get(column).getJavaType() == Integer.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Integer value: %s", this.operator, this.column, values.get(0)));
					return cb.notEqual(root.get(column).as(Integer.class), Integer.parseInt(values.get(0)));
				}
				else
				if(root.get(column).getJavaType()== Date.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Date (format input:MM/dd/yyyy HH:mm:ss) value: %s", this.operator, this.column, values.get(0)));
					return cb.notEqual(root.get(column).as(Date.class), DateFunction.StringToDate(values.get(0), DateFunction.MMddyyyyHHmmss));
				}
				else
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type String value: %s", this.operator, this.column, values.get(0)));
					return cb.notLike(cb.trim(cb.lower(root.get(column))), "%" + values.get(0).trim().toLowerCase() + "%");
				}
				
			} else if (operator.equalsIgnoreCase("equal") || operator.equalsIgnoreCase("=")) {
				if(root.get(column).getJavaType() == Double.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Double value: %s", this.operator, this.column, values.get(0)));
					return cb.equal(root.get(column).as(Double.class), Double.parseDouble(values.get(0)));					
				}
				else
				if(root.get(column).getJavaType() == Integer.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Integer value: %s", this.operator, this.column, values.get(0)));
					return cb.equal(root.get(column).as(Integer.class), Integer.parseInt(values.get(0)));					
				}
				else
				if(root.get(column).getJavaType() == Boolean.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Boolean value: %s", this.operator, this.column, values.get(0)));
					return cb.equal(root.get(column).as(Boolean.class), values.get(0).matches("1|true|True")? true : false );					
				}
				else
				if(root.get(column).getJavaType()== Date.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Date (format input:MM/dd/yyyy HH:mm:ss) value: %s", this.operator, this.column, values.get(0)));
					return cb.equal(root.get(column).as(Date.class), DateFunction.StringToDate(values.get(0), DateFunction.MMddyyyyHHmmss));
				}
				else
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type String value: %s", this.operator, this.column, values.get(0)));
					return cb.equal(cb.trim(cb.lower(root.get(column))), values.get(0).trim().toLowerCase());
				}				

			} else if (operator.equalsIgnoreCase("notequal") || operator.equalsIgnoreCase("<>")) {
				if(root.get(column).getJavaType() == Double.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Double value: %s", this.operator, this.column, values.get(0)));
					return cb.notEqual(root.get(column).as(Double.class), Double.parseDouble(values.get(0)));					
				}
				else
				if(root.get(column).getJavaType() == Integer.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Integer value: %s", this.operator, this.column, values.get(0)));
					return cb.notEqual(root.get(column).as(Integer.class), Integer.parseInt(values.get(0)));					
				}
				else
				if(root.get(column).getJavaType() == Boolean.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Boolean value: %s", this.operator, this.column, values.get(0)));
					return cb.notEqual(root.get(column).as(Boolean.class), values.get(0).matches("1|true|True")? true : false );					
				}
				else
				if(root.get(column).getJavaType()== Date.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Date (format input:MM/dd/yyyy HH:mm:ss) value: %s", this.operator, this.column, values.get(0)));
					return cb.notEqual(root.get(column).as(Date.class), DateFunction.StringToDate(values.get(0), DateFunction.MMddyyyyHHmmss));
				}
				else
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type String value: %s", this.operator, this.column, values.get(0)));
					return cb.notEqual(cb.trim(cb.lower(root.get(column))), values.get(0).trim().toLowerCase());
				}
				
			} else if (operator.equalsIgnoreCase("in")) {
				
				if(root.get(column).getJavaType() == Integer.class || root.get(column).getJavaType() == Double.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Integer array or Double array values: %s", this.operator, this.column,  String.join("|", values)));
					return root.get(column).in(values);
				}	
				
				log.info(String.format("Query operation \"%s\" on column \"%s\" type String array values: %s", this.operator, this.column,  String.join("|", values)));
				ListIterator<String> iterator = values.listIterator();
				while (iterator.hasNext()) {
					iterator.set(iterator.next().toString().trim().toLowerCase());
				}
				
				return cb.trim(cb.lower(root.get(column))).in(values);

			} else if (operator.equalsIgnoreCase("between")) {
				if (root.get(column).getJavaType() == Double.class) {
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Double values: %s ", this.operator, this.column,  String.join("|", values)));
					return cb.between(root.get(column).as(Double.class), Double.parseDouble(values.get(0)), Double.parseDouble(values.get(1)));
					
				}
				else if (root.get(column).getJavaType() == Integer.class) {
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Integer values between %s ", this.operator, this.column,  String.join("|", values)));
					return cb.between(root.get(column).as(Integer.class), Integer.parseInt(values.get(0)),  Integer.parseInt(values.get(1)));
					
				} else if (root.get(column).getJavaType() == Date.class) {
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Date (format input:MM/dd/yyyy HH:mm:ss) values: %s ", this.operator, this.column,  String.join("|", values)));
					return cb.between(root.get(column).as(Date.class),
							DateFunction.StringToDate(values.get(0), DateFunction.MMddyyyyHHmmss),
							DateFunction.StringToDate(values.get(1), DateFunction.MMddyyyyHHmmss));
				}
			
			} else if (operator.equalsIgnoreCase("greaterthan") || operator.equalsIgnoreCase(">")) {
				if(root.get(column).getJavaType() == Double.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Double value: %s", this.operator, this.column, values.get(0)));
					return cb.gt(root.get(column).as(Double.class), Double.parseDouble(values.get(0)));					
				}
				else
				if(root.get(column).getJavaType() == Integer.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Integer value: %s", this.operator, this.column, values.get(0)));
					return cb.gt(root.get(column).as(Integer.class), Integer.parseInt(values.get(0)));					
				}
				else
				if(root.get(column).getJavaType()== Date.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Date (format input:MM/dd/yyyy HH:mm:ss) value: %s", this.operator, this.column, values.get(0)));
					return cb.greaterThan(root.get(column).as(Date.class), DateFunction.StringToDate(values.get(0), DateFunction.MMddyyyyHHmmss));
				}
				else
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type String value: %s", this.operator, this.column, values.get(0)));
					return cb.greaterThan(cb.trim(cb.lower(root.get(column))), values.get(0).trim().toLowerCase());
				}
				
			} else if (operator.equalsIgnoreCase("greaterthanorequal") || operator.equalsIgnoreCase(">=")) {
				if(root.get(column).getJavaType() == Double.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Double value: %s", this.operator, this.column, values.get(0)));
					return cb.ge(root.get(column).as(Double.class), Double.parseDouble(values.get(0)));					
				}
				else
				if(root.get(column).getJavaType() == Integer.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Integer value: %s", this.operator, this.column, values.get(0)));
					return cb.ge(root.get(column).as(Integer.class), Integer.parseInt(values.get(0)));					
				}
				else
				if(root.get(column).getJavaType()== Date.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Date (format input:MM/dd/yyyy HH:mm:ss) value: %s", this.operator, this.column, values.get(0)));
					return cb.greaterThanOrEqualTo(root.get(column).as(Date.class), DateFunction.StringToDate(values.get(0), DateFunction.MMddyyyyHHmmss));
				}
				else
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type String value: %s", this.operator, this.column, values.get(0)));
					return cb.greaterThanOrEqualTo(cb.trim(cb.lower(root.get(column))), values.get(0).trim().toLowerCase());
				}
				
			} else if (operator.equalsIgnoreCase("lessthan") || operator.equalsIgnoreCase("<")) {
				if(root.get(column).getJavaType() == Double.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Double value: %s", this.operator, this.column, values.get(0)));
					return cb.lt(root.get(column).as(Double.class), Double.parseDouble(values.get(0)));					
				}
				else
				if(root.get(column).getJavaType() == Integer.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Integer value: %s", this.operator, this.column, values.get(0)));
					return cb.lt(root.get(column).as(Integer.class), Integer.parseInt(values.get(0)));					
				}
				else
				if(root.get(column).getJavaType()== Date.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Date (format input:MM/dd/yyyy HH:mm:ss) value: %s", this.operator, this.column, values.get(0)));
					return cb.lessThan(root.get(column).as(Date.class), DateFunction.StringToDate(values.get(0), DateFunction.MMddyyyyHHmmss));
				}
				else
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type String value: %s", this.operator, this.column, values.get(0)));
					return cb.lessThan(cb.trim(cb.lower(root.get(column))), values.get(0).trim().toLowerCase());
				}
				
			} else if (operator.equalsIgnoreCase("lessthanorequal") || operator.equalsIgnoreCase("<=")) {
				if(root.get(column).getJavaType() == Double.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Double value: %s", this.operator, this.column, values.get(0)));
					return cb.le(root.get(column).as(Double.class), Double.parseDouble(values.get(0)));					
				}
				else
				if(root.get(column).getJavaType() == Integer.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Integer value: %s", this.operator, this.column, values.get(0)));
					return cb.le(root.get(column).as(Integer.class), Integer.parseInt(values.get(0)));					
				}
				else
				if(root.get(column).getJavaType()== Date.class)
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type Date (format input:MM/dd/yyyy HH:mm:ss) value: %s", this.operator, this.column, values.get(0)));
					return cb.lessThanOrEqualTo(root.get(column).as(Date.class), DateFunction.StringToDate(values.get(0), DateFunction.MMddyyyyHHmmss));
				}
				else
				{
					log.info(String.format("Query operation \"%s\" on column \"%s\" type String value: %s", this.operator, this.column, values.get(0)));
					return cb.lessThanOrEqualTo(cb.trim(cb.lower(root.get(column))), values.get(0).trim().toLowerCase());
				}

			}
		}

		return null;
	}
}
