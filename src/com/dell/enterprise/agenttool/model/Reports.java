/*
 * FILENAME
 *     Desktop.java
 *
 * FILE LOCATION
 *     $Source$
 *
 * VERSION
 *     $Id$
 *         @version       $Revision$
 *         Check-Out Tag: $Name$
 *         Locked By:     $Lockers$
 *
 * FORMATTING NOTES
 *     * Lines should be limited to 120 characters.
 *     * Files should contain no tabs.
 *     * Indent code using four-character increments.
 *
 * COPYRIGHT
 *     Copyright (C) 2010 HEB. All rights reserved.
 *     This software is the confidential and proprietary information of
 *     HEB ("Confidential Information"). You shall not
 *     disclose such Confidential Information and shall use it only in
 *     accordance with the terms of the licence agreement you entered into
 *     with HEB.
 */

package com.dell.enterprise.agenttool.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import java.util.Map;

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

/**
 * <p>
 * TODO Add one-sentence summarising the class functionality here; this sentence
 * should only contain one full-stop.
 * </p>
 * <p>
 * TODO Add detailed HTML description of class here, including the following,
 * where relevant:
 * <ul>
 * <li>TODO Description of what the class does and how it is done.</li>
 * <li>TODO Explanatory notes on usage, including code examples.</li>
 * <li>TODO Notes on class dynamic behaviour e.g. is it thread-safe?</li>
 * </ul>
 * </p>
 * <p>
 * <h2>Responsibilities</h2>
 * </p>
 * <p>
 * <ul>
 * <li>TODO Reponsibility #1.</li>
 * <li>TODO Reponsibility #2.</li>
 * <li>TODO Reponsibility #3.</li>
 * <li>TODO Reponsibility #4.</li>
 * </ul>
 * </p>
 * <p>
 * <h2>Protected Interface</h2>
 * </p>
 * <p>
 * TODO Document the protected interface here, including the following:
 * <ul>
 * <li>TODO Which aspects of the class's functionality can be extended.</li>
 * <li>TODO How this extension is done, including usage examples.</li>
 * </ul>
 * </p>
 * 
 * @see TODO Related Information
 * 
 * @author thuynguyen
 * 
 * @version $Id$
 **/
public class Reports implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String item_SKU;
    private String category_id;
    private String manufacturer_id;
    private String mfg_part_number;
    private String name;
    private String image_url;
    private String short_description;
    private String long_description;
    private String weight;
    private String download_filename;
    private String received_by;
    private String received_date;
    private String warehouse_location;
    private String status;
    private String status_date;
    private String list_price;
    private String modified_price;
    private String modified_date;
    private String warranty_date;
    private String flagType;
    private String lease_number;
    private String contract_number;
    private String mfg_SKU;
    private String notes;
    private String ship_via;
    private String attribute01;
    private String attribute02;
    private String attribute03;
    private String attribute04;
    private String attribute05;
    private String attribute06;
    private String attribute07;
    private String attribute08;
    private String attribute09;
    private String attribute10;
    private String attribute11;
    private String attribute12;
    private String attribute13;
    private String attribute14;
    private String attribute15;
    private String attribute16;
    private String attribute17;
    private String attribute18;
    private String attribute19;
    private String attribute20;
    private String attribute21;
    private String attribute22;
    private String attribute23;
    private String attribute24;
    private String attribute25;
    private String attribute26;
    private String attribute27;
    private String attribute28;
    private String attribute29;
    private String attribute30;
    private String attribute31;
    private String attribute32;
    private String attribute33;
    private String attribute34;
    private String attribute35;
    private String attribute36;
    private String attribute37;
    private String attribute38;
    private String attribute39;
    private String attribute40;
    private String order_number;
    private String tracking_number;
    private String ship_date;
    private String origin;
    
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getItem_SKU() {
		return item_SKU;
	}
	public void setItem_SKU(String itemSKU) {
		item_SKU = itemSKU;
	}
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String categoryId) {
		category_id = categoryId;
	}
	public String getManufacturer_id() {
		return manufacturer_id;
	}
	public void setManufacturer_id(String manufacturerId) {
		manufacturer_id = manufacturerId;
	}
	public String getMfg_part_number() {
		return mfg_part_number;
	}
	public void setMfg_part_number(String mfgPartNumber) {
		mfg_part_number = mfgPartNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String imageUrl) {
		image_url = imageUrl;
	}
	public String getShort_description() {
		return short_description;
	}
	public void setShort_description(String shortDescription) {
		short_description = shortDescription;
	}
	public String getLong_description() {
		return long_description;
	}
	public void setLong_description(String longDescription) {
		long_description = longDescription;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getDownload_filename() {
		return download_filename;
	}
	public void setDownload_filename(String downloadFilename) {
		download_filename = downloadFilename;
	}
	public String getReceived_by() {
		return received_by;
	}
	public void setReceived_by(String receivedBy) {
		received_by = receivedBy;
	}
	public String getReceived_date() {
		return received_date;
	}
	public void setReceived_date(String receivedDate) {
		received_date = receivedDate;
	}
	public String getWarehouse_location() {
		return warehouse_location;
	}
	public void setWarehouse_location(String warehouseLocation) {
		warehouse_location = warehouseLocation;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus_date() {
		return status_date;
	}
	public void setStatus_date(String statusDate) {
		status_date = statusDate;
	}
	public String getList_price() {
		return list_price;
	}
	public void setList_price(String listPrice) {
		list_price = listPrice;
	}
	public String getModified_price() {
		return modified_price;
	}
	public void setModified_price(String modifiedPrice) {
		modified_price = modifiedPrice;
	}
	public String getModified_date() {
		return modified_date;
	}
	public void setModified_date(String modifiedDate) {
		modified_date = modifiedDate;
	}
	public String getWarranty_date() {
		return warranty_date;
	}
	public void setWarranty_date(String warrantyDate) {
		warranty_date = warrantyDate;
	}
	public String getFlagType() {
		return flagType;
	}
	public void setFlagType(String flagType) {
		this.flagType = flagType;
	}
	public String getLease_number() {
		return lease_number;
	}
	public void setLease_number(String leaseNumber) {
		lease_number = leaseNumber;
	}
	public String getContract_number() {
		return contract_number;
	}
	public void setContract_number(String contractNumber) {
		contract_number = contractNumber;
	}
	public String getMfg_SKU() {
		return mfg_SKU;
	}
	public void setMfg_SKU(String mfgSKU) {
		mfg_SKU = mfgSKU;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getShip_via() {
		return ship_via;
	}
	public void setShip_via(String shipVia) {
		ship_via = shipVia;
	}
	public String getAttribute01() {
		return attribute01;
	}
	public void setAttribute01(String attribute01) {
		this.attribute01 = attribute01;
	}
	public String getAttribute02() {
		return attribute02;
	}
	public void setAttribute02(String attribute02) {
		this.attribute02 = attribute02;
	}
	public String getAttribute03() {
		return attribute03;
	}
	public void setAttribute03(String attribute03) {
		this.attribute03 = attribute03;
	}
	public String getAttribute04() {
		return attribute04;
	}
	public void setAttribute04(String attribute04) {
		this.attribute04 = attribute04;
	}
	public String getAttribute05() {
		return attribute05;
	}
	public void setAttribute05(String attribute05) {
		this.attribute05 = attribute05;
	}
	public String getAttribute06() {
		return attribute06;
	}
	public void setAttribute06(String attribute06) {
		this.attribute06 = attribute06;
	}
	public String getAttribute07() {
		return attribute07;
	}
	public void setAttribute07(String attribute07) {
		this.attribute07 = attribute07;
	}
	public String getAttribute08() {
		return attribute08;
	}
	public void setAttribute08(String attribute08) {
		this.attribute08 = attribute08;
	}
	public String getAttribute09() {
		return attribute09;
	}
	public void setAttribute09(String attribute09) {
		this.attribute09 = attribute09;
	}
	public String getAttribute10() {
		return attribute10;
	}
	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}
	public String getAttribute11() {
		return attribute11;
	}
	public void setAttribute11(String attribute11) {
		this.attribute11 = attribute11;
	}
	public String getAttribute12() {
		return attribute12;
	}
	public void setAttribute12(String attribute12) {
		this.attribute12 = attribute12;
	}
	public String getAttribute13() {
		return attribute13;
	}
	public void setAttribute13(String attribute13) {
		this.attribute13 = attribute13;
	}
	public String getAttribute14() {
		return attribute14;
	}
	public void setAttribute14(String attribute14) {
		this.attribute14 = attribute14;
	}
	public String getAttribute15() {
		return attribute15;
	}
	public void setAttribute15(String attribute15) {
		this.attribute15 = attribute15;
	}
	public String getAttribute16() {
		return attribute16;
	}
	public void setAttribute16(String attribute16) {
		this.attribute16 = attribute16;
	}
	public String getAttribute17() {
		return attribute17;
	}
	public void setAttribute17(String attribute17) {
		this.attribute17 = attribute17;
	}
	public String getAttribute18() {
		return attribute18;
	}
	public void setAttribute18(String attribute18) {
		this.attribute18 = attribute18;
	}
	public String getAttribute19() {
		return attribute19;
	}
	public void setAttribute19(String attribute19) {
		this.attribute19 = attribute19;
	}
	public String getAttribute20() {
		return attribute20;
	}
	public void setAttribute20(String attribute20) {
		this.attribute20 = attribute20;
	}
	public String getAttribute21() {
		return attribute21;
	}
	public void setAttribute21(String attribute21) {
		this.attribute21 = attribute21;
	}
	public String getAttribute22() {
		return attribute22;
	}
	public void setAttribute22(String attribute22) {
		this.attribute22 = attribute22;
	}
	public String getAttribute23() {
		return attribute23;
	}
	public void setAttribute23(String attribute23) {
		this.attribute23 = attribute23;
	}
	public String getAttribute24() {
		return attribute24;
	}
	public void setAttribute24(String attribute24) {
		this.attribute24 = attribute24;
	}
	public String getAttribute25() {
		return attribute25;
	}
	public void setAttribute25(String attribute25) {
		this.attribute25 = attribute25;
	}
	public String getAttribute26() {
		return attribute26;
	}
	public void setAttribute26(String attribute26) {
		this.attribute26 = attribute26;
	}
	public String getAttribute27() {
		return attribute27;
	}
	public void setAttribute27(String attribute27) {
		this.attribute27 = attribute27;
	}
	public String getAttribute28() {
		return attribute28;
	}
	public void setAttribute28(String attribute28) {
		this.attribute28 = attribute28;
	}
	public String getAttribute29() {
		return attribute29;
	}
	public void setAttribute29(String attribute29) {
		this.attribute29 = attribute29;
	}
	public String getAttribute30() {
		return attribute30;
	}
	public void setAttribute30(String attribute30) {
		this.attribute30 = attribute30;
	}
	public String getAttribute31() {
		return attribute31;
	}
	public void setAttribute31(String attribute31) {
		this.attribute31 = attribute31;
	}
	public String getAttribute32() {
		return attribute32;
	}
	public void setAttribute32(String attribute32) {
		this.attribute32 = attribute32;
	}
	public String getAttribute33() {
		return attribute33;
	}
	public void setAttribute33(String attribute33) {
		this.attribute33 = attribute33;
	}
	public String getAttribute34() {
		return attribute34;
	}
	public void setAttribute34(String attribute34) {
		this.attribute34 = attribute34;
	}
	public String getAttribute35() {
		return attribute35;
	}
	public void setAttribute35(String attribute35) {
		this.attribute35 = attribute35;
	}
	public String getAttribute36() {
		return attribute36;
	}
	public void setAttribute36(String attribute36) {
		this.attribute36 = attribute36;
	}
	public String getAttribute37() {
		return attribute37;
	}
	public void setAttribute37(String attribute37) {
		this.attribute37 = attribute37;
	}
	public String getAttribute38() {
		return attribute38;
	}
	public void setAttribute38(String attribute38) {
		this.attribute38 = attribute38;
	}
	public String getAttribute39() {
		return attribute39;
	}
	public void setAttribute39(String attribute39) {
		this.attribute39 = attribute39;
	}
	public String getAttribute40() {
		return attribute40;
	}
	public void setAttribute40(String attribute40) {
		this.attribute40 = attribute40;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String orderNumber) {
		order_number = orderNumber;
	}
	public String getTracking_number() {
		return tracking_number;
	}
	public void setTracking_number(String trackingNumber) {
		tracking_number = trackingNumber;
	}
	public String getShip_date() {
		return ship_date;
	}
	public void setShip_date(String shipDate) {
		ship_date = shipDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	


	
}
