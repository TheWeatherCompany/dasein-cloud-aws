
package org.dasein.cloud.aws;

import java.math.BigDecimal;

import javax.annotation.Nonnull;

import org.dasein.cloud.CloudPrice;

/**
 * Represents the current price of a specific kind of resource. This is used by getPrice() methods in different
 * support objects so you can quickly fetch the price of a of cloud resources without having to worry about pricing details or matrix. Fetching the status is a good way to check for large-scale state changes which may need to be
 * more frequent than detailed state changes.
 */

public class AWSPrice extends CloudPrice {
   
    public AWSPrice(@Nonnull String regionId,  @Nonnull String resourceId, @Nonnull BigDecimal price) {
    	this.providerId = "AWS";
    	this.regionId = regionId;
        this.resourceId = resourceId;
        this.price = price;
    }
   
    
    @Override
	public @Nonnull String getPriceDescription() {
    	return "The Price of AWS "+ resourceId + " in Region: "+ regionId +" is "+ price;
    }

}
