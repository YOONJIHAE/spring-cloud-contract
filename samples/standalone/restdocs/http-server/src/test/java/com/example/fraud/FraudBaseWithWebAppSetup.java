/*
 * Copyright 2013-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// tag::base_class[]
package com.example.fraud;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest(classes = Application.class)
public abstract class FraudBaseWithWebAppSetup {

	@Autowired
	private WebApplicationContext context;

	@BeforeEach
	public void setup(TestInfo info, RestDocumentationContextProvider restDocumentation) {
		RestAssuredMockMvc.mockMvc(MockMvcBuilders.webAppContextSetup(this.context)
				.apply(documentationConfiguration(restDocumentation))
				.alwaysDo(document(
						getClass().getSimpleName() + "_" + info.getDisplayName()))
				.build());
	}

	protected void assertThatRejectionReasonIsNull(Object rejectionReason) {
		assert rejectionReason == null;
	}

}
// end::base_class[]
