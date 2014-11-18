package org.dasein.cloud.aws.quotas.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

/**
 * User: mgulimonov
 * Date: 17.11.2014
 */
public class TrustedAdvisorChecks {

    @JsonProperty("checks")
    private List<TrustedAdvisorCheckDescription> checks;

    public List<TrustedAdvisorCheckDescription> getChecks() {
        return checks;
    }

    public void setChecks(List<TrustedAdvisorCheckDescription> checks) {
        if (checks == null) {
            this.checks = Collections.emptyList();
        } else {
            this.checks = checks;
        }
    }

    public TrustedAdvisorCheckDescription getServiceLimitDescription() {
        for (TrustedAdvisorCheckDescription check : getChecks()) {
            if ("Service Limits".equalsIgnoreCase(check.getName())) {
                return check;
            }
        }

        return null;
    }
}
