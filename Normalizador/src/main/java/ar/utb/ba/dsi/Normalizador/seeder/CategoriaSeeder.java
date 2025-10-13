package ar.utb.ba.dsi.Normalizador.seeder;

import ar.utb.ba.dsi.Normalizador.models.entities.Categoria;
import ar.utb.ba.dsi.Normalizador.models.repositories.ICategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoriaSeeder implements CommandLineRunner {

  private final ICategoriaRepository categoriaRepository;

  @Override
  public void run(String... args) throws Exception {
    if (categoriaRepository.count() == 0) {
      List<Categoria> categorias = new ArrayList<>();

      categorias.add(createCategoria("Meteorológicos extremos", Arrays.asList(
          "Precipitación de granizo", "Calor extremo", "Granizo de gran tamaño", "Vientos huracanados",
          "Fenómeno meteorológico con granizo", "Temporal de viento", "Tormenta de viento",
          "Emergencia por altas temperaturas", "Vendaval", "Temporal", "Fenómeno de calor intenso",
          "Vientos con fuerza ciclónica", "Tormenta con fuertes vientos", "Fenómeno de viento rotativo",
          "Ola de calor extremo", "Tormenta eléctrica", "Torbellino", "Granizada destructiva",
          "Frente tormentoso", "Ráfagas destructivas", "Tornado", "Ráfagas de más de 100 km/h",
          "Tormenta severa", "Récord histórico de calor", "Temperaturas sofocantes",
          "Tormenta de granizo", "Embudo de viento", "Tormenta con piedras de granizo", "Temperaturas récord",
          "Tormenta tropical", "Remolino de aire", "Manga de viento"
      )));

      categorias.add(createCategoria("Sísmicos y geodinámicos", Arrays.asList(
          "Derrumbe de cerro", "Desprendimiento de ladera", "Avalancha de lodo", "Alud",
          "Movimiento telúrico", "Aluvión de tierra y rocas", "Deslizamiento de tierra",
          "Sismo con epicentro local", "Sismo de gran magnitud", "Temblor", "Réplica sísmica",
          "Corrimiento de tierra", "Evento sísmico", "Terremoto destructivo"
      )));

      categorias.add(createCategoria("Hidrometeorológicos", Arrays.asList(
          "Inundación por lluvias intensas", "Anegamiento masivo", "Lluvia torrencial",
          "Crisis hídrica", "Desborde de arroyo", "Emergencia por sequía", "Desborde de río",
          "Lluvia de hielo", "Crecida histórica", "Inundación repentina", "Precipitación de nieve",
          "Sequía prolongada", "Escasez de agua", "Crecida de aguas subterráneas",
          "Sequía con pérdidas agrícolas", "Déficit hídrico", "Inundación en zona urbana",
          "Sequía extrema"
      )));

      categorias.add(createCategoria("Incendios y degradación ambiental", Arrays.asList(
          "Fuego en área protegida", "Incendio en reserva natural", "Quema de pastizales",
          "Quema descontrolada", "Fuego en bosque nativo", "Incendio forestal",
          "Fuego arrasador en zona boscosa", "Incendio en zona de monte"
      )));

      categorias.add(createCategoria("Volcánicos", Arrays.asList(
          "Caída de ceniza", "Emisión volcánica", "Polvo volcánico en suspensión",
          "Lluvia de ceniza volcánica", "Contaminación por ceniza volcánica",
          "Precipitación de material volcánico", "Nube de ceniza"
      )));

      categorias.add(createCategoria("Nivosos y criogénicos", Arrays.asList(
          "Fenómeno de frío intenso", "Helada fuera de temporada", "Helada severa",
          "Copiosa caída de nieve", "Congelamiento", "Helada destructiva", "Nevada histórica", "Nevada extrema", "Ventisca con nieve",
          "Tormenta de nieve", "Temperaturas bajo cero", "Nevazón", "Nevada fuera de temporada",
          "Frío extremo"
      )));

      categorias.add(createCategoria("Otro", new ArrayList<>()));

      categoriaRepository.saveAll(categorias);
      System.out.println("Categorías cargadas exitosamente: " + categorias.size());
    } else {
      System.out.println("Las categorías ya están cargadas en la base de datos");
    }
  }

  private Categoria createCategoria(String nombre, List<String> categoriasExternas) {
    Categoria categoria = new Categoria();
    categoria.setNombre(nombre);
    categoria.setCategoriasExternas(new ArrayList<>(categoriasExternas));
    return categoria;
  }
}