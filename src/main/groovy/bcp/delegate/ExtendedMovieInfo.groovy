package bcp.delegate

import com.fasterxml.jackson.annotation.JsonIgnore

import groovy.transform.CompileStatic
import groovy.transform.ToString
import groovy.transform.builder.Builder

@ToString
@CompileStatic
@Builder
class ExtendedMovieInfo {

	String director;

	@Delegate
	@JsonIgnore
	MovieInfo baseInfo = new MovieInfo();

	
}

