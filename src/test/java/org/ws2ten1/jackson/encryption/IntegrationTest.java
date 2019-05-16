/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ws2ten1.jackson.encryption;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.skyscreamer.jsonassert.JSONAssert;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * TODO miyamoto.daisuke.
 */
@Slf4j
@RunWith(Parameterized.class)
public class IntegrationTest {
	
	private static final ObjectMapper MAPPER = new ObjectMapper()
		.registerModule(new JacksonEncryptingDeserializationModule(new ExampleTextEncryptor()))
		.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
	
	
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
			{
				// string
				"{ 'foo': 'FOO', 'bar': 'BAR' }",
				new Entity("FOO", "IkJBUiI="),
			},
			{
				// null
				"{ 'foo': 'FOO', 'bar': null }",
				new Entity("FOO", "bnVsbA=="),
			},
			{
				// boolean
				"{ 'foo': 'FOO', 'bar': true }",
				new Entity("FOO", "dHJ1ZQ=="),
			},
			{
				// number
				"{ 'foo': 'FOO', 'bar': 10 }",
				new Entity("FOO", "MTA="),
			},
			{
				// real
				"{ 'foo': 'FOO', 'bar': 1.0 }",
				new Entity("FOO", "MS4w"),
			},
			{
				// object
				"{ 'foo': 'FOO', 'bar': { 'foo': 'FOO', 'bar': 'BAR' } }",
				new Entity("FOO", "eyJmb28iOiJGT08iLCJiYXIiOiJCQVIifQ=="),
			},
			{
				// array
				"{ 'foo': 'FOO', 'bar': [ 'FOO', 'BAR' ] }",
				new Entity("FOO", "WyJGT08iLCJCQVIiXQ=="),
			},
		});
	}
	
	
	@Parameter(0)
	public String json; // -@cs[VisibilityModifier]
	
	@Parameter(1)
	public Object object; // -@cs[VisibilityModifier]
	
	
	@Test
	public void testDeserialize() throws Exception {
		// exercise
		Object actual = MAPPER.readValue(json, object.getClass());
		// verify
		log.info("[{}] deserialized to [{}]", json, actual);
		assertThat(actual).isEqualTo(object);
	}
	
	@Test
	public void testSerialize() throws Exception {
		// exercise
		String actual = MAPPER.writeValueAsString(object);
		// verify
		log.info("[{}] serialized to [{}]", object, actual);
		JSONAssert.assertEquals(json, actual, true);
	}
	
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	static class Entity {
		
		private String foo;
		
		@EncryptedField
		private String bar;
	}
}
