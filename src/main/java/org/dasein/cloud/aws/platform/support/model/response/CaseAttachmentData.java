package org.dasein.cloud.aws.platform.support.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dasein.cloud.platform.support.model.TicketAttachmentData;

/**
 * User: Eugene Yaroslavtsev
 * Date: 20.08.2014
 */
@JsonInclude(Include.NON_NULL)
public class CaseAttachmentData {

    @JsonProperty("data")
    private String data;

    @JsonProperty("fileName")
    private String fileName;

    @JsonProperty(value = "__type")
    private String type;

    public TicketAttachmentData build() {
        TicketAttachmentData response = new TicketAttachmentData();
        response.setData(data);
        response.setFileName(fileName);
        return response;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
