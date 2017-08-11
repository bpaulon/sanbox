package bcp.delegate;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class DelegateJsonTests {

	public class DateModule extends SimpleModule {
		  private static final String NAME = "CustomIntervalModule";
		  //private final VersionUtil VERSION_UTIL = new VersionUtil() {};

		  public DateModule() {
		    super(NAME, new VersionUtil() {}.version());
		    addSerializer(LocalDate.class, new LocalDateSerializer());
		    addDeserializer(LocalDate.class, new LocalDateDeserializer());
		  }
		}
	
	public class CustomObjectMapper extends ObjectMapper {
		  public CustomObjectMapper() {
		    registerModule(new DateModule());
		    enable(SerializationFeature.INDENT_OUTPUT);
		  }
		}
	
	@Test
	public void delegateSerializationShouldFlattenJson() throws JsonGenerationException, JsonMappingException, IOException {
		
		ExtendedMovieInfo eb = new ExtendedMovieInfo();
		eb.setDirector("James Cameron");
		
		MovieInfo mi = new MovieInfo();
		mi.setName("Alien");
		mi.setReleaseDate(LocalDate.of(1987,  1, 1));
		eb.setBaseInfo(mi);
		
		System.out.println(eb.toString());
		
		CustomObjectMapper mapper = new CustomObjectMapper();
		String s= mapper.writeValueAsString(eb);
		
		System.out.println(s);
	}
	
	@Test
	public void delegateSerialization() throws JsonParseException, JsonMappingException, IOException {
		String json = "{\"director\" : \"James Cameron\",\"name\" : \"Alien\",\"releaseDate\" : \"1987-01-01\"}";
		CustomObjectMapper mapper = new CustomObjectMapper();
		ExtendedMovieInfo emi = mapper.readValue(json, ExtendedMovieInfo.class);
		
		System.out.println(emi);
	}

}
