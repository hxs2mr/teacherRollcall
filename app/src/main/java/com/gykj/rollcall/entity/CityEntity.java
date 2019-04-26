package com.gykj.rollcall.entity;

/**
 * desc   : 城市实体对象
 * author : josh.lu
 * e-mail : 1113799552@qq.com
 * date   : 2019/1/413:00
 * version: 1.0
 */
public class CityEntity {


    /**
     * status : OK
     * result : {"location":{"lng":106.660237,"lat":26.70687},"formatted_address":"贵州省贵阳市白云区贵阳绕城高速公路","business":"","addressComponent":{"city":"贵阳市","direction":"","distance":"","district":"白云区","province":"贵州省","street":"贵阳绕城高速公路","street_number":""},"cityCode":146}
     */

    private String status;
    private ResultBean result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * location : {"lng":106.660237,"lat":26.70687}
         * formatted_address : 贵州省贵阳市白云区贵阳绕城高速公路
         * business :
         * addressComponent : {"city":"贵阳市","direction":"","distance":"","district":"白云区","province":"贵州省","street":"贵阳绕城高速公路","street_number":""}
         * cityCode : 146
         */

        private LocationBean location;
        private String formatted_address;
        private String business;
        private AddressComponentBean addressComponent;
        private int cityCode;

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public String getBusiness() {
            return business;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public AddressComponentBean getAddressComponent() {
            return addressComponent;
        }

        public void setAddressComponent(AddressComponentBean addressComponent) {
            this.addressComponent = addressComponent;
        }

        public int getCityCode() {
            return cityCode;
        }

        public void setCityCode(int cityCode) {
            this.cityCode = cityCode;
        }

        public static class LocationBean {
            /**
             * lng : 106.660237
             * lat : 26.70687
             */

            private double lng;
            private double lat;

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }
        }

        public static class AddressComponentBean {
            /**
             * city : 贵阳市
             * direction :
             * distance :
             * district : 白云区
             * province : 贵州省
             * street : 贵阳绕城高速公路
             * street_number :
             */

            private String city;
            private String direction;
            private String distance;
            private String district;
            private String province;
            private String street;
            private String street_number;

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDirection() {
                return direction;
            }

            public void setDirection(String direction) {
                this.direction = direction;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getStreet() {
                return street;
            }

            public void setStreet(String street) {
                this.street = street;
            }

            public String getStreet_number() {
                return street_number;
            }

            public void setStreet_number(String street_number) {
                this.street_number = street_number;
            }
        }
    }
}
