package org.dasein.cloud.aws.platform.support.model.response;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.dasein.cloud.platform.support.model.TicketAttachmentData;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Eugene Yaroslavtsev
 * Date: 20.08.2014
 */
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({
        "__type",
        "data",
        "fileName"
})
public class CaseAttachmentDataResponse {

    @JsonProperty(value = "__type")
    private String type;
    @JsonProperty("data")
    private String data;
    @JsonProperty("fileName")
    private String fileName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonIgnore
    public TicketAttachmentData buildAttachmentData() {
        TicketAttachmentData response = new TicketAttachmentData();
        response.setData(data);
        response.setFileName(fileName);
        return response;
    }

    @JsonProperty(value = "__type")
    public String getType() {
        return type;
    }

    @JsonProperty(value = "__type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("data")
    public String getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(String data) {
        this.data = data;
    }

    @JsonProperty("fileName")
    public String getFileName() {
        return fileName;
    }

    @JsonProperty("fileName")
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
