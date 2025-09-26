package org.shark.boot12.user.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.shark.boot12.user.enums.LogLevel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "access_log")
@DynamicUpdate  // Dirty Checking 시 변경된 필드만 UPDATE 쿼리로 생성됩니다. 

@Getter
@Setter
public class AccessLog {

  @Id
  @TableGenerator(
      name = "accessLogIdGen"
    , table = "access_log_seq"
    , pkColumnName = "entity"
    , pkColumnValue = "accessLog"
    , valueColumnName = "nextval"
    , initialValue = 0
    , allocationSize = 1
  )
  @GeneratedValue(generator = "accessLogIdGen")
  @Column(name = "access_id")
  private Long id;
  
  @Column(name = "log_type")
  private String logType;
  
  @Column(name = "log_message")
  private String logMessage;
  
  @Enumerated(EnumType.ORDINAL)
  @Column(name = "log_level")
  private LogLevel logLevel;
  
  private String ip;
  
  @Column(name = "user_agent")
  private String userAgent;
  
  @CreationTimestamp
  @Column(name = "created_at")
  private LocalDateTime createdAt;
  
  protected AccessLog() {}
  
  public static AccessLog createAccessLog(String logType, String logMessage, LogLevel logLevel, String ip, String userAgent) {
    AccessLog accessLog = new AccessLog();
    accessLog.logType = logType;
    accessLog.logMessage = logMessage;
    accessLog.logLevel = logLevel;
    accessLog.ip = ip;
    accessLog.userAgent = userAgent;
    return accessLog;
  }

  @Override
  public String toString() {
    return "AccessLog [id=" + id + ", logType=" + logType + ", logMessage=" + logMessage + ", logLevel=" + logLevel
        + ", ip=" + ip + ", userAgent=" + userAgent + ", createdAt=" + createdAt + "]";
  }
  
}
