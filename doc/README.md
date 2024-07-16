# Trabajo Práctico Anual Integrador

## Justificaciones

Docs: [justificaciones de diseño](https://docs.google.com/document/d/1sZnbGAiAdFFzCzarOC5EWZcYKkOKYYnlncrYzPZRU_g/edit#heading=h.s88isqhub9g8)

El presente documento se redacta con el objetivo de justificar el diseño y, además, aclarar sobre
decisiones e interpretaciones realizadas sobre casos donde el requerimiento no especifica, o
presenta cierta ambigüedad. Cabe destacar que se trata de una descripción completa de todo el
modelado, y no "sobre la entrega"; asimismo, los planteos de previas entregas serán borradas o
corregidas según el caso.

### Colaboradores

Para el registro de colaboradores se dispone de una clase `Colaborador`, el cual se usa para los dos
tipos de colaboradores que existen en el dominio (Humano y Jurídico). Es posible diferenciarlos
mediante el atributo `tipoColaborador`; de esta forma se evita también realizar lecturas sobre
campos `null` y, eventualmente causar `NullPointerException`, este último se debe a que existen
algunos campos de información compartida como algunos que no, con lo cual, una instancia de
colaborador tendrá siempre ciertos atributos nulos, ya que no corresponden al colaborador en
cuestión. Ejemplo: `razónSocial` y `fechaNacimiento`, `razónSocial` corresponde únicamente a una
entidad jurídica mientras que `fechaNacimiento` pertenece únicamente a una persona humana.

Como requerimiento del dominio, se debe poder adicionar campos al formulario de registro; es decir,
además de los datos básicos, la ONG a cargo del sistema debe poder definir nuevos campos del
formulario. Para la realización de éste, se define una clase `Fomulario`, el cual contiene una lista
de preguntas, y otra clase `FormularioRepondido` que tiene las respuestas y el formulario al que
respondió. Los colaboradores simplemente tendrán una instancia del `FormularioRepondido`.

![](https://github.com/dds-utn/2024-tpa-ma-ma-grupo-22/blob/main/doc/images/esquema-formulario.png)

### Heladeras

### Colaboraciones

### Tarjetas

### Incidentes

### Mensajería

### Reportes Semanales

### Migración

### Aclaraciones

## Diagramas

## Interfaces de Usuario

Figma: [diseño de UI](https://www.figma.com/file/EyW4OGnq34TSm3ipW3f4Wj/Untitled?type=design&node-id=0%3A1&mode=design&t=csZUTdQlz1pFMskw-1)
