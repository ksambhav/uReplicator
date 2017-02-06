package utils

/**
  * Created by ben on 5/17/16.
  */

//import play.api.Logger
import play.api.libs.json.JsObject
//import org.slf4j.{ LoggerFactory}
import org.apache.log4j.Logger

object LogUtil {
  val logger =  Logger.getLogger("request.not.processed")
  val logger_decompress_failed = Logger.getLogger("decompress.failed")

  val logger_custom = Logger.getLogger("sdk.log.custom")
  val logger_utm = Logger.getLogger("sdk.log.utm")
  val logger_web = Logger.getLogger("sdk.log.web")
  val logger_mobile = Logger.getLogger("sdk.log.mobile")
  val logger_sdk_stat = Logger.getLogger("sdk.log.stat")
  val logger_other = Logger.getLogger("sdk.log.action.other")

  val EMPTY_REQUEST_BODY = 600
  val EMPTY_REQUEST_DATA = 601
  val INVALID_REQUEST_HEADER = 602
  val DECOMPRESS_FAILED = 603
  val EMPTY_DATA = 604
  val ENCODING_ERROR = 605
  val DECOMPRESS_TIMEOUT_FAILED = 606
  val INVALID_JSON = 701
  val INVALID_JSON_ARRAY = 702
  val EVENTS_PROCESS_ERROR = 800
  val EVENTS_WITHOUT_AI = 801
  val EVENTS_WITHOUT_U = 802
  val EVENTS_WITHOUT_T = 803
  val EVENTS_WITHOUT_E = 804
  val UNKNOWN_ERROR = 900

  val platform_Android = "Android"
  val platform_iOS = "iOS"
  val platform_Web = "Web"
  val platform_mina = "MINA"

  val LOG_TOPIC_UTM = "utm"
  val LOG_TOPIC_DEVICE = "devices"
  val LOG_TOPIC_WEB_PV = "web-pv"
  val LOG_TOPIC_WEB_ACTION = "web-action"
  val LOG_TOPIC_WEB_ACTION_OTHER = "web-action-other"
  val LOG_TOPIC_MINA_PV = "mina-pv"
  val LOG_TOPIC_MINA_ACTION = "mina-action"
  val LOG_TOPIC_MINA_ACTION_OTHER = "mina-action-other"
  val LOG_TOPIC_MOBILE_PV = "mobile-pv"
  val LOG_TOPIC_MOBILE_ACTION = "mobile-action"
  val LOG_TOPIC_MOBILE_ACTION_OTHER = "mobile-action-other"
  val LOG_TOPIC_CUSTOM_ACTION = "custom-action"
  val LOG_TOPIC_SDK_STAT = "sdk-stat"

  val LOG_KAFKA_EMPTY_KEY = ""

  val EMPTY_BODY = ""

  def Log(logInfo: JsObject, code: Int, platform: String): Unit = {
    val ai = (logInfo \ "ai").as[String]
    val uri = (logInfo \ "uri").as[String]
    val refer = (logInfo \ "referer").as[String]
    val ip = (logInfo \ "ip").as[String]
    val ua = (logInfo \ "ua").as[String]
    val stm = (logInfo \ "stm").as[Long]

    logger.error(s"$ai\01$platform\01$code\01$uri\01$ip\01$refer\01$ua\01$stm")
  }

  def Log(message: => String) {
    logger.error(message)
  }

  def Log(topic: String, message: String, partition: String): Unit = {
    topic match {
      case LOG_TOPIC_CUSTOM_ACTION => logger_custom.info(topic + "|" + partition + message.toString())
      case LOG_TOPIC_DEVICE => logger_utm.info(topic + "|" + partition + message)
      case LOG_TOPIC_UTM => logger_utm.info(topic + "|" + partition + message)
      case LOG_TOPIC_WEB_PV => logger_web.info(topic + "|" + partition + message)
      case LOG_TOPIC_WEB_ACTION => logger_web.info(topic + "|" + partition + message)
      case LOG_TOPIC_MOBILE_PV => logger_mobile.info(topic + "|" + partition + message)
      case LOG_TOPIC_MOBILE_ACTION => logger_mobile.info(topic + "|" + partition + message)
      case LOG_TOPIC_SDK_STAT => logger_sdk_stat.info(topic + "|" + partition + message)
      case LOG_TOPIC_MOBILE_ACTION_OTHER => logger_other.info(topic + "|" + partition + message)
      case LOG_TOPIC_WEB_ACTION_OTHER => logger_other.info(topic + "|" + partition + message)
      case LOG_TOPIC_MINA_PV => logger_mobile.info(topic + "|" + partition + message)
      case LOG_TOPIC_MINA_ACTION => logger_mobile.info(topic + "|" + partition + message)
      case LOG_TOPIC_MINA_ACTION_OTHER => logger_mobile.info(topic + "|" + partition + message)
      case _ => // do nothing
    }
  }


  def Log_DecompressFailed(message: => String, platform: String): Unit =  {
    logger_decompress_failed.error(s"[$platform]$message")
  }
}
