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

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * This is for testing only!  This is not encryptor!
 */
public class ExampleTextEncryptor implements TextEncryptor {
	
	@Override
	public String encrypt(String text) {
		return Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
	}
	
	@Override
	public String decrypt(String encryptedText) {
		return new String(Base64.getDecoder().decode(encryptedText), StandardCharsets.UTF_8);
	}
}
