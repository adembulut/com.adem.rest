<#assign multiTab>,${'\n\t\t\t\t\t\t\t'} </#assign>
    ${method.annotationName}
    public ResponseEntity<${method.returnTypeName}> ${method.methodName}(<#list method.parameterList as param>${(param_index==0)?then('',multiTab)}${param.annotation} ${param.type} ${param.name}</#list>){

        <#if method.voidMethod>
        ${method.serviceFieldName}.${method.methodName}(<#list method.serviceParameterListSorted as item>${(item_index==0)?then('',', ')}${item.name}</#list>);
        return ResponseEntity.ok().build();
            <#else>
        return ResponseEntity.ok(${method.serviceFieldName}.${method.methodName}(<#list method.serviceParameterListSorted as item>${(item_index==0)?then('',', ')}${item.name}</#list>));
        </#if>
    }