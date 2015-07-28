//
// Built on Thu Jul 23 09:26:46 CEST 2015 by logback-translator
// For more information on configuration files in Groovy
// please see http://logback.qos.ch/manual/groovy.html

// For assistance related to this tool or configuration files
// in general, please contact the logback user mailing list at
//    http://qos.ch/mailman/listinfo/logback-user

// For professional support please see
//   http://www.qos.ch/shop/products/professionalSupport

import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.FileAppender

import ch.qos.logback.classic.Level;

	Properties properties = new Properties();
	InputStream st=Thread.currentThread().getContextClassLoader().getResourceAsStream('cmt_check.properties');
	if (st!=null){
		properties.load(st);
	}
	
	def Boolean useFile=false;
	def Boolean useConsole=false;
	def List<String> appenders=[];
	def Level logLevel=null;
	
	if ( (properties["log.fileName"]!=null) && (!"".equals(properties["log.fileName"]))){
		useFile=true;
	}
	
	useConsole=(properties["log.console"]=="true")? true:false;
	
	if (useFile){
		appender("FILE", RollingFileAppender) {
			file = properties["log.fileName"];
			encoder(PatternLayoutEncoder) {
			  pattern = "%date %level [%thread] %logger{40} [%file:%line] %msg%n";
			}
		  
			rollingPolicy(FixedWindowRollingPolicy) {
		    	fileNamePattern = "tests.%i.log"
		    	minIndex = 1
		    	maxIndex = 3
	  		}
	  		
	  		triggeringPolicy(SizeBasedTriggeringPolicy) {
   				maxFileSize = "3MB"
  			}
				  
		}
	}
	
	appender("STDOUT", ConsoleAppender) {
	  encoder(PatternLayoutEncoder) {
	   pattern = "%date %level [%thread] %logger{40} [%file:%line] %msg%n";
	  }
	}
	
	
	
	if (useFile){
		appenders.add("FILE");
	} 
	if (useConsole) {
		appenders.add("STDOUT");
	}
	
	logLevel=Level.toLevel(properties["log.level"],INFO);
	root(logLevel, appenders);
	