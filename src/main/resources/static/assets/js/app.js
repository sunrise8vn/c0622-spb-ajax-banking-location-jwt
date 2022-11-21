class App {
    static DOMAIN_SERVER = "http://localhost:8095";
    static CUSTOMER_API = this.DOMAIN_SERVER + "/api/customers";
    static DEPOSIT_API = this.DOMAIN_SERVER + "/api/deposits";
}

class LocationRegion {
    constructor(id, provinceId, provinceName, districtId, districtName, wardId, wardName, address) {
        this.id = id;
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.wardId = wardId;
        this.wardName = wardName;
        this.address = address;
    }
}

class Customer {
    constructor(id, avatar, fullName, email, phone, locationRegion, balance, deleted) {
        this.id = id;
        this.avatar = avatar;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.locationRegion = locationRegion;
        this.balance = balance;
        this.deleted = deleted;
    }
}

class Deposit {
    constructor(id, transactionAmount, customerId) {
        this.id = id;
        this.transactionAmount = transactionAmount;
        this.customerId = customerId;
    }
}