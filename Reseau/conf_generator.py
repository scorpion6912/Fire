import yaml
from jinja2 import Template

# Load YAML configuration
with open('routers_variables.yml', 'r') as yaml_file:
    data = yaml.load(yaml_file, Loader=yaml.FullLoader)

# Load Jinja2 template
with open('routers_template.j2', 'r') as template_file:
    template_content = template_file.read()
    template = Template(template_content)

# Render template with YAML data

for router in data :
    result = template.render(router)

    with open(f'routers_config/{router["router"]}_config.txt', 'w') as f:
        f.write(result)

    print(f'Configuration generated successfully. Check {router["router"]}_config.txt.')
