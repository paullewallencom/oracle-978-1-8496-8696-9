package com.packt.store.audit;

import java.util.Date;

public class AuditEvent {
	   private final long timestamp = System.currentTimeMillis();
	   private String message;
	   private Object[] params = null;

	   public AuditEvent(String message, Object[] params) {
	      this.setMessage(message);
	      this.setParams(params);
	   }

	   public String toString() {
	      StringBuilder sb = new StringBuilder();

	      sb.append("[").append(new Date(timestamp)).append("] - ")
		                            .append(getMessage());

	      if (getParams() != null) {
	         sb.append("- Param value(s): ");

	         for (Object o : getParams())
	            sb.append(o).append(",");
	            sb.deleteCharAt(sb.length() - 1);
	         }
	         return sb.toString();
	      }
		// getters and setters

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	}
