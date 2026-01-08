package ar.utn.dssi.FuenteDinamica.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${directorioDeGuardado}")
    private String directorioDeGuardado;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Convertimos la ruta configurada en una ruta absoluta de sistema de archivos
        Path rutaPath = Paths.get(directorioDeGuardado).toAbsolutePath().normalize();
        String rutaFinal = "file:" + rutaPath.toString() + "/";

        // Mapeamos la URL /uploads/** a la carpeta física
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(rutaFinal);
    }
}
