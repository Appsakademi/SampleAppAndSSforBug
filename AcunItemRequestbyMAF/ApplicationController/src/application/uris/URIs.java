package application.uris;

public class URIs {
    private static final String ITEM_REQUEST_HEADER_URI = "/IrItemRequestHeaderView";

    //departments URI
    public static String GetAllItemRequestHeaderURI() {
        return ITEM_REQUEST_HEADER_URI;
    }

    public static String GetAllItemRequestHeaderByIdURI(Integer headerId) {
        return ITEM_REQUEST_HEADER_URI + "/" + headerId;
    }


    public static String PatchDepartmentsUpdateURI() {
        return ITEM_REQUEST_HEADER_URI;
    }

    public static String PostDepartmentsCreateURI() {
        return ITEM_REQUEST_HEADER_URI;
    }

    public static String DeleteDepartmentsRemoveURI(Integer headerId) {
        return ITEM_REQUEST_HEADER_URI + "remove/" + headerId;
    }
}
