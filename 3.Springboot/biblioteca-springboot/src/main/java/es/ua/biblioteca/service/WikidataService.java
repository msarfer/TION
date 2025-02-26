package es.ua.biblioteca.service;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.ModelFactory ;
import org.springframework.stereotype.Service;

@Service
public class WikidataService {

    public static String sparqlRepository = "https://query.wikidata.org/sparql";

	public List<Map<String, String>> getAuthors(int num, String nombre) {

		List<Map<String, String>> resultado = null;

		String queryString =
				"PREFIX wdt: <http://www.wikidata.org/prop/direct/> " +
						"PREFIX wikibase: <http://wikiba.se/ontology#> " +
						"PREFIX bd: <http://www.bigdata.com/rdf#> " +
						"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
						"SELECT ?author ?image ?nombre ?apellido ?nacionalidad ?fechaNacimiento WHERE { " +
						"  SERVICE <https://query.wikidata.org/sparql> { " +
						"    SELECT DISTINCT ?author ?image ?nombre ?apellido ?nacionalidad ?fechaNacimiento WHERE { " +
						"      ?author wdt:P2799 ?idBvmc. " +
						"      ?author wdt:P18 ?image. " +
						"      ?author wdt:P735 ?nombreEntity. " +
						"      ?nombreEntity rdfs:label ?nombre. " +
						"      ?author wdt:P734 ?apellidoEntity. " +
						"      ?apellidoEntity rdfs:label ?apellido. " +
						"      ?author wdt:P27 ?nacionalidadEntity. " +
						"      ?nacionalidadEntity rdfs:label ?nacionalidad. " +
						"      ?author wdt:P569 ?fechaNacimiento. " +
						"      FILTER(LANG(?nombre) = \"es\") " +
						"      FILTER(LANG(?apellido) = \"es\") " +
						"      FILTER(LANG(?nacionalidad) = \"es\") " +
						"      FILTER(CONTAINS(LCASE(?nombre), \"" + nombre.toLowerCase() + "\")) " +
						"    } " +
						"    LIMIT " + num +
						"  } " +
						"}";

		Query query = QueryFactory.create(queryString) ;
		try (QueryExecution qexec = QueryExecutionFactory.create(query, ModelFactory.createDefaultModel())) {
			ResultSet rs = qexec.execSelect() ;

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			ResultSetFormatter.outputAsJSON(outputStream, rs);

			String jsonString = new String(outputStream.toByteArray());

			ObjectMapper mapper = new ObjectMapper();
			ResultData resultData = mapper.readValue(jsonString, ResultData.class);

			List<Map<String, String>> autores = new ArrayList<>();
			for (Binding binding : resultData.results.bindings) {
				Map<String, String> autorInfo = new HashMap<>();
				autorInfo.put("nombre", binding.nombre.value);
				autorInfo.put("imagen", binding.image.value);
				autorInfo.put("apellido", binding.apellido.value);
				autorInfo.put("nacionalidad", binding.nacionalidad.value);

				Instant instant = Instant.parse(binding.fechaNacimiento.value);
				LocalDateTime dateTime = instant.atZone(ZoneOffset.UTC).toLocalDateTime();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				String formattedDate = dateTime.format(formatter);
				autorInfo.put("fechaNacimiento", formattedDate);
				autores.add(autorInfo);
			}
			resultado = autores;
		} catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return resultado;
	}
}

@JsonIgnoreProperties(ignoreUnknown = true)
class ResultData {
	public Results results;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Results {
	public List<Binding> bindings;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Binding {
	public Item author;
	public Item image;
	public Item nombre;
	public Item apellido;
	public Item nacionalidad;
	public Item fechaNacimiento;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Item {
	public String value;
}