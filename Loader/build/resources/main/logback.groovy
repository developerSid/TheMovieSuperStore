import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy

import static ch.qos.logback.classic.Level.DEBUG
import static ch.qos.logback.classic.Level.ERROR
import static ch.qos.logback.classic.Level.INFO

def baseLogFileName = 'ExampleLogback'
def logFileHome = System.getProperty('log.dir', "/tmp/log/${baseLogFileName}")
def enabledLoggers = ['STDOUT', 'FILE']

if(enabledLoggers.contains('FILE'))
{
   def logFileDir = new File(logFileHome)

   if(logFileDir.mkdirs() || logFileDir.exists()) //create the directory structure.  This may not be strictly necessary, but I've had issues in the past with Logback not doing this where Log4j would
   {
      appender('FILE', RollingFileAppender) {  //configures appender to append to a log file and to roll once per day and keep up to a maximum of 7 days worth of logs
         file = "${logFileHome}/${baseLogFileName}.log"
         append = true
         encoder(PatternLayoutEncoder) {
            pattern = '%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n'
         }
         rollingPolicy(TimeBasedRollingPolicy) {
            FileNamePattern = "${logFileHome}/${baseLogFileName}.%d{yyyy-MM-dd}.log"
            maxHistory = 7
         }
      }
   }
   else
   {
      System.err.printf('Unable to create logging directory %s\n', logFileHome)
   }
}

if(enabledLoggers.contains('STDOUT'))
{
   appender('STDOUT', ConsoleAppender) {
      encoder(PatternLayoutEncoder) {
         pattern = '%d{HH:mm:ss.SSS} %-5level %logger - %msg%n'
      }
   }
}

// this can be changed to any number of things.  These are the ones that I carried around most of the time.
logger('org.springframework', ERROR)
logger('org.eclipse', ERROR)
logger('org.apache', ERROR)
logger('org.hibernate', ERROR)
logger('jndi', ERROR)

root(DEBUG, enabledLoggers)
