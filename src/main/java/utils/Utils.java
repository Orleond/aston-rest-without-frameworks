package utils;

import jakarta.servlet.http.HttpServletRequest;

public class Utils {

    public static boolean requestIsValid(HttpServletRequest request) {
        final String type = request.getParameter("type");
        final String brand = request.getParameter("brand_id");
        final String model = request.getParameter("model_id");

        return type != null && !type.isEmpty() &&
                brand != null && !brand.isEmpty() &&
                model != null && !model.isEmpty();
    }

    private Utils() {
    }
}
