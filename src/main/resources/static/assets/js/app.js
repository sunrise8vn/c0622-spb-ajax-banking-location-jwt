class App {
    static DOMAIN_SERVER = "http://localhost:8098";
    static CUSTOMER_API = this.DOMAIN_SERVER + "/api/customers";
    static PRODUCT_API = this.DOMAIN_SERVER + "/api/products";
    static DEPOSIT_API = this.DOMAIN_SERVER + "/api/deposits";
    static SERVER_CLOUDINARY = "https://res.cloudinary.com/toanphat/image/upload";
    static SCALE_W250_H250_Q100 = "/c_limit,w_250,h_250,q_100";
    static SCALE_W288_H216_Q100 = "c_limit,w_288,h_216,q_100";
    static SCALE_W80_H80_Q100 = "/c_limit,w_80,h_80,q_100";
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



class ProductAvatar {
    constructor(fileName, fileFolder, fileUrl) {
        this.fileName = fileName;
        this.fileFolder = fileFolder;
        this.fileUrl = fileUrl;
    }
}

class Product {
    constructor(id, title, price, unit, description, avatar) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.unit = unit;
        this.description = description;
        this.avatar = avatar;
    }
}