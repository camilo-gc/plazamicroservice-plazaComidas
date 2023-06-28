package com.pragma.powerup.plazamicroservice.configuration;


public class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    public static final Long OWNER_ROLE_ID = 2L;
    public static final String RESPONSE_MESSAGE_KEY = "message";
    public static final String RESTAURANT_CREATED_MESSAGE = "Restaurant created successfully";
    public static final String RESPONSE_ERROR_MESSAGE_KEY = "error";
    public static final String WRONG_CREDENTIALS_MESSAGE = "Wrong credentials";
    public static final String NO_DATA_FOUND_MESSAGE = "No data found for the requested petition";
    public static final String ROLE_NOT_ALLOWED_MESSAGE = "No permission granted to create restaurant with this owner";
    public static final String USER_ALREADY_EXISTS_MESSAGE = "A user already exists with the dni provided";
    public static final String OWNER_NOT_FOUND_MESSAGE = "Owner not found";
    public static final String UNAUTHORIZED_OWNER_VALIDATION_MESSAGE = "not authorized for owner validation";
    public static final String INTERNAL_ERROR_OWNER_VALIDATION_MESSAGE = "internal error validating owner";
    public static final String SWAGGER_TITLE_MESSAGE = "Plaza API Pragma Power Up";
    public static final String SWAGGER_DESCRIPTION_MESSAGE = "Plaza microservice";
    public static final String SWAGGER_VERSION_MESSAGE = "0.9.2";
    public static final String SWAGGER_LICENSE_NAME_MESSAGE = "Apache 2.0";
    public static final String SWAGGER_LICENSE_URL_MESSAGE = "http://springdoc.org";
    public static final String SWAGGER_TERMS_OF_SERVICE_MESSAGE = "http://swagger.io/terms/";

    public static final String FIELD_VALIDATION = "The field value is not valid";
    public static final String FIELD_NAME = "name";

    public static final String RESTAURANT_NOT_FOUND_MESSAGE = "Restaurant not found";

    public static final String DISH_CREATED_MESSAGE = "Dish created successfully";
    public static final String DISH_NOT_FOUND_MESSAGE = "Dish not found";
    public static final String DISH_UPDATED_MESSAGE = "Dish updated successfully";
    public static final String OWNER_NOT_AUTHORIZED_MESSAGE = "Owner not authorized";
    public static final String EMPLOYEE_CREATED_MESSAGE = "Employee added successfully";

    public static final String CATEGORY_NOT_FOUND_MESSAGE = "Category not found";

    public static final String ORDER_STATUS_PENDING = "Pending";
    public static final String ORDER_STATUS_PREPARATION = "Preparation";
    public static final String ORDER_STATUS_CANCELED = "Canceled";
    public static final String ORDER_STATUS_READY = "Ready";
    public static final String ORDER_STATUS_DELIVERED = "Delivered";

    public static final String ORDER_IN_PROCESS_MESSAGE = "orders already exist for this client";
    public static final String ORDER_CREATED_MESSAGE = "Order created successfully";


    public static final String DISH_IS_NOT_IN_RESTAURANT_MESSAGE = "one of the dishes not belong to the restaurant";


    public static final String ORDER_NOT_FOUND_MESSAGE = "Order not found";
    public static final String ORDER_LIST_EMPTY_MESSAGE = "one of the dishes not belong to the restaurant";
    public static final String ORDER_READY_MESSAGE = "Your order is ready";

    public static final String SENT_CODE_KEY = "sent_code";
    public static final String VERIFICATION_STATUS_KEY = "verification_status";
    public static final String APPROVED_STATUS = "approved";
    public static final String SENT_CODE_NOT_APPROVED_MESSAGE = "Sent code not approved";
    public static final String ORDER_IS_NOT_READY = "Order is not ready";
    public static final String ORDER_IS_NOT_IN_PREPARATION = "Order is not in preparation";
    public static final String ORDER_IS_NOT_PENDING = "Order is not pending";
    public static final String ORDER_CANCEL_MESSAGE = "your order was canceled";

}
