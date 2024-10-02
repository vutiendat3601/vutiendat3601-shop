package vn.io.vutiendat3601.shop.v2.common;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
// import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    servers = {
      @Server(url = "https://apidev.shopsinhvien.io.vn", description = "dev"),
      @Server(url = "http://localho.st:8080", description = "local"),
    },
    info =
        @Info(
            title = "Shop Sinh Vien APIs",
            version = "2.0",
            description = "Shop Sinh Vien Shop APIs version 2"))
public class OpenApiConfig {}
