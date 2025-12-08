package ar.utn.dssi.FuenteDinamica.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${directorioDeGuardado}") // Toma el valor de application.properties
    private String directorioDeGuardado;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Magia: Convierte la ruta de tu disco (sea cual sea) a una URL web
        String rutaAbsoluta = Paths.get(directorioDeGuardado).toAbsolutePath().toUri().toString();

        System.out.println("--> EXPONIENDO IMAGENES DESDE: " + rutaAbsoluta);

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(rutaAbsoluta);
    }
}