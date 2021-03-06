package info.cepheus.showcase_axon_exception.config;


import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private List<Parameter> buildParameter() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("Authorization").description("Bearer Token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        return pars;
    }

    @Bean
    public Docket personInformationApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("person").apiInfo(personApiInfo()).select()
                .paths(personPaths()).build().globalOperationParameters(buildParameter());
    }

    private ApiInfo personApiInfo() {
        return new ApiInfoBuilder().title("person api").description("person api information")
                .termsOfServiceUrl("http://springfox.io").license("Apache License Version 2.0")
                .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE").version("2.0").build();
    }

    private Predicate<String> personPaths() {
        return or(regex("/api/person.*"));
    }

}

