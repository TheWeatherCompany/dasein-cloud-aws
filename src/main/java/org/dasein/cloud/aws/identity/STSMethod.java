/**
 * Copyright (C) 2009-2013 Dell, Inc.
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

package org.dasein.cloud.aws.identity;

import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.aws.AWSCloud;
import org.dasein.cloud.aws.compute.EC2Method;
import org.dasein.cloud.identity.IdentityAndAccessSupport;
import org.dasein.cloud.identity.ServiceAction;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * EC2Method for working with the AWS Security Token Service - http://docs.aws.amazon.com/STS/latest/APIReference/Welcome.html.
 *
 * @author Cameron Stokes (http://github.com/clstokes)
 * @since 2013-10-04
 */
public class STSMethod extends EC2Method {

  static public final String STS_PREFIX = "sts:";
  static public final String STS_URL = "https://sts.amazonaws.com";
  static public final String VERSION = "2011-06-15";

  static public final String ASSUME_ROLE = "AssumeRole";

  static public @Nonnull ServiceAction[] asIAMServiceAction( @Nonnull String action ) {
    if ( action.equals( ASSUME_ROLE ) ) {
      return new ServiceAction[] {IdentityAndAccessSupport.ASSUME_ROLE};
    }
    return new ServiceAction[0];
  }

  public STSMethod( AWSCloud provider, Map<String, String> parameters ) throws CloudException, InternalException {
    super( provider, STS_URL, parameters );
  }

}
