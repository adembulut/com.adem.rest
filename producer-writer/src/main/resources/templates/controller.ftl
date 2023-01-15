package ${packageName};

<#list importSet as item>
import ${item.name};
</#list>

@RestController
@RequestMapping("/${baseUrl}")
public class ${className}Controller {

    @Autowired
    private ${serviceName} ${serviceFieldName};

<#list mappingCodes as item>
${item}
</#list>


}