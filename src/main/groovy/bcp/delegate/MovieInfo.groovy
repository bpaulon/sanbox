package bcp.delegate

import java.time.LocalDate

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import groovy.transform.ToString
import groovy.transform.builder.Builder

@ToString
@Builder
class MovieInfo {

	LocalDate releaseDate;
	
	String name;
}
