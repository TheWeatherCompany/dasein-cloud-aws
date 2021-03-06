/**
 * Copyright (C) 2009-2014 Dell, Inc.
 * See annotations for authorship information
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */

package org.dasein.cloud.aws.admin;

import org.dasein.cloud.admin.AbstractAdminServices;
import org.dasein.cloud.aws.AWSCloud;

public class AWSAdminServices extends AbstractAdminServices {
    private AWSCloud cloud;
    
    public AWSAdminServices(AWSCloud cloud) { this.cloud = cloud; }
    
    @Override
    public ReservedInstance getPrepaymentSupport() {
        return new ReservedInstance(cloud);
    }
}
