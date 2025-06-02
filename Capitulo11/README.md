# Práctica 10. Monitoreo de microservicio cliente con Grafana y Prometheus
En este laboratorio se espera que el alumno pueda implementar una arquitectura de microservicios en docker con monitoreo incluido. 

## Objetivos de la práctica:
- Usar docker compose para crear arquitectura.
- Validar configuración de Prometheus.
- Crear Dashboard en Grafana.

## Duración aproximada:
- 60 minutos.

---

<div style="width: 400px;">
        <table width="50%">
            <tr>
                <td style="text-align: center;">
                    <a href="../Capitulo10/"><img src="../images/anterior.png" width="40px"></a>
                    <br>anterior
                </td>
                <td style="text-align: center;">
                   <a href="../README.md">Lista Laboratorios</a>
                </td>
<td style="text-align: center;">
                    <a href=""><img src="../images/siguiente.png" width="40px"></a>
                    <br>siguiente
                </td>
            </tr>
        </table>
</div>

---


## Diagrama

![diagrama](../images/10/diagrama.png)


## Instrucciones

Esta práctica se encuentra separada en las siguientes secciones: 

- **[Configurar Grafana y Prometheus](#configurar-grafana-y-prometheus-return)**

- **[Crear Dashboard en Grafana](#crear-dashboard-en-grafana-return)**

## Configurar Grafana y Prometheus [return](#instrucciones)

1. Crea una carpeta con el nombre de **grafana y prometheus** en el escritorio.
2. Abre la carpeta en **Visual Studio Code**.
3. Añade los siguientes archivos en la carpeta:

![alt text](../images/10/1.png)

4. En el archivo **dashboard.json** agrega el siguiente contenido: 

```json
{
  "__inputs": [
    {
      "name": "DS_PROMETHEUS",
      "label": "prometheus",
      "description": "",
      "type": "datasource",
      "pluginId": "prometheus",
      "pluginName": "Prometheus"
    }
  ],
  "__elements": {},
  "__requires": [
    {
      "type": "panel",
      "id": "bargauge",
      "name": "Bar gauge",
      "version": ""
    },
    {
      "type": "grafana",
      "id": "grafana",
      "name": "Grafana",
      "version": "11.4.0"
    },
    {
      "type": "datasource",
      "id": "prometheus",
      "name": "Prometheus",
      "version": "1.0.0"
    },
    {
      "type": "panel",
      "id": "stat",
      "name": "Stat",
      "version": ""
    },
    {
      "type": "panel",
      "id": "timeseries",
      "name": "Time series",
      "version": ""
    }
  ],
  "annotations": {
    "list": [
      {
        "builtIn": 1,
        "datasource": {
          "type": "grafana",
          "uid": "-- Grafana --"
        },
        "enable": true,
        "hide": true,
        "iconColor": "rgba(0, 211, 255, 1)",
        "name": "Annotations & Alerts",
        "type": "dashboard"
      }
    ]
  },
  "editable": true,
  "fiscalYearStartMonth": 0,
  "graphTooltip": 0,
  "id": null,
  "links": [],
  "panels": [
    {
      "datasource": {
        "type": "prometheus",
        "uid": "${DS_PROMETHEUS}"
      },
      "fieldConfig": {
        "defaults": {
          "color": {
            "mode": "palette-classic"
          },
          "mappings": [],
          "thresholds": {
            "mode": "absolute",
            "steps": [
              {
                "color": "green",
                "value": null
              },
              {
                "color": "red",
                "value": 80
              }
            ]
          }
        },
        "overrides": []
      },
      "gridPos": {
        "h": 6,
        "w": 9,
        "x": 0,
        "y": 0
      },
      "id": 2,
      "options": {
        "displayMode": "lcd",
        "legend": {
          "calcs": [],
          "displayMode": "list",
          "placement": "bottom",
          "showLegend": false
        },
        "maxVizHeight": 300,
        "minVizHeight": 16,
        "minVizWidth": 8,
        "namePlacement": "auto",
        "orientation": "auto",
        "reduceOptions": {
          "calcs": [
            "lastNotNull"
          ],
          "fields": "",
          "values": false
        },
        "showUnfilled": true,
        "sizing": "auto",
        "valueMode": "text"
      },
      "pluginVersion": "11.4.0",
      "targets": [
        {
          "editorMode": "code",
          "expr": "sum  by (level) (logback_events_total{job=\"microserviceclient\"})",
          "legendFormat": "__auto",
          "range": true,
          "refId": "A",
          "datasource": {
            "type": "prometheus",
            "uid": "${DS_PROMETHEUS}"
          }
        }
      ],
      "title": "Total logs",
      "type": "bargauge"
    },
    {
      "datasource": {
        "type": "prometheus",
        "uid": "${DS_PROMETHEUS}"
      },
      "fieldConfig": {
        "defaults": {
          "color": {
            "mode": "thresholds"
          },
          "mappings": [
            {
              "options": {
                "0": {
                  "color": "red",
                  "index": 1,
                  "text": "DOWN"
                },
                "1": {
                  "color": "green",
                  "index": 0,
                  "text": "UP"
                }
              },
              "type": "value"
            },
            {
              "options": {
                "match": "null",
                "result": {
                  "color": "red",
                  "index": 2,
                  "text": "DOWN"
                }
              },
              "type": "special"
            }
          ],
          "thresholds": {
            "mode": "absolute",
            "steps": [
              {
                "color": "green",
                "value": null
              },
              {
                "color": "red",
                "value": 80
              }
            ]
          }
        },
        "overrides": []
      },
      "gridPos": {
        "h": 6,
        "w": 10,
        "x": 9,
        "y": 0
      },
      "id": 1,
      "options": {
        "colorMode": "value",
        "graphMode": "area",
        "justifyMode": "auto",
        "orientation": "auto",
        "percentChangeColorMode": "standard",
        "reduceOptions": {
          "calcs": [
            "lastNotNull"
          ],
          "fields": "",
          "values": false
        },
        "showPercentChange": false,
        "textMode": "auto",
        "wideLayout": true
      },
      "pluginVersion": "11.4.0",
      "targets": [
        {
          "datasource": {
            "type": "prometheus",
            "uid": "${DS_PROMETHEUS}"
          },
          "editorMode": "code",
          "expr": "up{instance=\"microclient:8082\"}",
          "legendFormat": "__auto",
          "range": true,
          "refId": "A"
        }
      ],
      "title": "Microservice status",
      "type": "stat"
    },
    {
      "datasource": {
        "type": "prometheus",
        "uid": "${DS_PROMETHEUS}"
      },
      "fieldConfig": {
        "defaults": {
          "color": {
            "mode": "palette-classic"
          },
          "custom": {
            "axisBorderShow": false,
            "axisCenteredZero": false,
            "axisColorMode": "text",
            "axisLabel": "",
            "axisPlacement": "auto",
            "barAlignment": 0,
            "barWidthFactor": 0.6,
            "drawStyle": "line",
            "fillOpacity": 0,
            "gradientMode": "none",
            "hideFrom": {
              "legend": false,
              "tooltip": false,
              "viz": false
            },
            "insertNulls": false,
            "lineInterpolation": "linear",
            "lineWidth": 1,
            "pointSize": 5,
            "scaleDistribution": {
              "type": "linear"
            },
            "showPoints": "auto",
            "spanNulls": false,
            "stacking": {
              "group": "A",
              "mode": "none"
            },
            "thresholdsStyle": {
              "mode": "off"
            }
          },
          "mappings": [],
          "thresholds": {
            "mode": "absolute",
            "steps": [
              {
                "color": "green",
                "value": null
              },
              {
                "color": "red",
                "value": 80
              }
            ]
          }
        },
        "overrides": []
      },
      "gridPos": {
        "h": 6,
        "w": 9,
        "x": 0,
        "y": 6
      },
      "id": 3,
      "options": {
        "legend": {
          "calcs": [],
          "displayMode": "list",
          "placement": "bottom",
          "showLegend": true
        },
        "tooltip": {
          "mode": "single",
          "sort": "none"
        }
      },
      "pluginVersion": "11.4.0",
      "targets": [
        {
          "editorMode": "code",
          "expr": "100 * sum(rate(http_server_requests_seconds_count{job=\"microserviceclient\", status=~\"2..\"}[30m])) / sum(rate(http_server_requests_seconds_count[30m]))",
          "legendFormat": "Códigos 2XX",
          "range": true,
          "refId": "A",
          "datasource": {
            "type": "prometheus",
            "uid": "${DS_PROMETHEUS}"
          }
        }
      ],
      "title": "Tasa de códigos 2XX",
      "type": "timeseries"
    },
    {
      "datasource": {
        "type": "prometheus",
        "uid": "${DS_PROMETHEUS}"
      },
      "fieldConfig": {
        "defaults": {
          "color": {
            "mode": "continuous-reds"
          },
          "custom": {
            "axisBorderShow": false,
            "axisCenteredZero": false,
            "axisColorMode": "text",
            "axisLabel": "",
            "axisPlacement": "auto",
            "barAlignment": 0,
            "barWidthFactor": 0.6,
            "drawStyle": "line",
            "fillOpacity": 40,
            "gradientMode": "opacity",
            "hideFrom": {
              "legend": false,
              "tooltip": false,
              "viz": false
            },
            "insertNulls": false,
            "lineInterpolation": "linear",
            "lineWidth": 1,
            "pointSize": 5,
            "scaleDistribution": {
              "type": "linear"
            },
            "showPoints": "auto",
            "spanNulls": false,
            "stacking": {
              "group": "A",
              "mode": "none"
            },
            "thresholdsStyle": {
              "mode": "off"
            }
          },
          "mappings": [],
          "thresholds": {
            "mode": "absolute",
            "steps": [
              {
                "color": "green",
                "value": null
              },
              {
                "color": "red",
                "value": 80
              }
            ]
          }
        },
        "overrides": []
      },
      "gridPos": {
        "h": 6,
        "w": 10,
        "x": 9,
        "y": 6
      },
      "id": 4,
      "options": {
        "legend": {
          "calcs": [],
          "displayMode": "list",
          "placement": "bottom",
          "showLegend": true
        },
        "tooltip": {
          "mode": "single",
          "sort": "none"
        }
      },
      "pluginVersion": "11.4.0",
      "targets": [
        {
          "editorMode": "code",
          "expr": "100 * sum(rate(http_server_requests_seconds_count{job=\"microserviceclient\", status=~\"4..|5..\"}[30m])) / sum(rate(http_server_requests_seconds_count{job=\"microserviceclient\"}[30m]))",
          "legendFormat": "Códigos 4XX y 5XX",
          "range": true,
          "refId": "A",
          "datasource": {
            "type": "prometheus",
            "uid": "${DS_PROMETHEUS}"
          }
        }
      ],
      "title": "Tasa de códigos 4xx y 5XX",
      "type": "timeseries"
    }
  ],
  "refresh": "5s",
  "schemaVersion": 40,
  "tags": [],
  "templating": {
    "list": []
  },
  "time": {
    "from": "now-30m",
    "to": "now"
  },
  "timepicker": {},
  "timezone": "browser",
  "title": "dashboard",
  "uid": "bekli4tupj7y8b",
  "version": 4,
  "weekStart": ""
}
```

5. En el archivo **db.env** agrega el siguiente contenido: 

```properties
MYSQL_DATABASE=datadb
MYSQL_ROOT_PASSWORD=1234
```

6. En el archivo **docker-compose.yaml** agrega el siguiente contenido: 

```yaml
services:

  prometheus:
    container_name: prometheus
    image: edgardovefe/pgclase:prometheus
    volumes:
      - './prometheus.yaml:/etc/prometheus/prometheus.yml'
    depends_on:
      microclient:
        condition: service_healthy
    ports:
      - 9090:9090
    networks:
      - microservicenetwork
    
  grafana:
    container_name: grafana
    image: edgardovefe/pgclase:grafana
    ports:
      - 3000:3000
    environment:
      - GF_SECURITY_ADMIN_PASSWORD=pass
    volumes:
      - grafana-storage:/var/lib/grafana
    networks:
      - microservicenetwork


  mysqlserver:
    container_name: mysqlserver
    image: "mysql:8.0"
    env_file:
      - db.env
    networks:
      - microservicenetwork
    healthcheck:
      test: mysqladmin ping -uroot -p${MYSQL_ROOT_PASSWORD} -hlocalhost
  
  microclient:
    container_name: microclient
    image: edgardovefe/devsecops:microservicegrafana
    env_file:
      - micro-client.env
    networks:
      - microservicenetwork
    ports:
      - 8082:8082
    depends_on:
      mysqlserver:
        condition: service_healthy
    healthcheck:
      test: curl -f http://localhost:8082/client

volumes:
  grafana-storage:

networks:
  microservicenetwork:
    driver: bridge
    ipam:
      config:
        - subnet: 192.168.32.0/24
          gateway: 192.168.32.1

```

7. En el archivo **micro-client.env** agrega el siguiente contenido: 

```properties
DB_IP=mysqlserver
DB_NAME=datadb
CLIENT_ID=<your client id>
APP_SECRET=<your app secret>
TENANT_ID=<your tenant id>
```
> **IMPORTANTE:** Sustituye los valores por la información del **app registry de Azure**.


8. En el archivo **prometheus.yaml** agrega el siguiente contenido:


```yaml
scrape_configs:
  - job_name: 'microserviceclient'
    metrics_path: '/actuator/prometheus'
    scrape_interval: 5s
    static_configs:
      - targets: ['microclient:8082']
```

9. Abre una terminal en **Visual Studio Code.**

10. Ejecuta el siguiente comando (asegurarse de ejecutar el comando estando en la misma ruta donde se encuentra el archivo **docker-compose.yaml**):

```bash
docker-compose up -d
```

> **NOTA:** El comando anterior iniciará toda la infraestructura con nuestro microservicio cliente, Grafana y Prometheus (tarda un poco el comando). 


11. Valida que el comando tenga una salida similar a la siguiente: 


![alt text](../images/10/2.png)

12. Valida que los contenedores esten iniciados con el comando:

```bash
docker ps
```

![alt text](../images/10/3.png)

> **IMPORTANTE:** Deben de ser 4 contenedores iniciados. 


## Crear Dashboard en Grafana [return](#instrucciones)

1. Abre un explorador web y escribe la siguiente dirección **http://localhost:3000**

![alt text](../images/10/4.png)

2. Nos pedira un usuario y contraseña:

- user: **admin**
- password: **pass**

3. Estando dentro de la herramienta, busca la opción  de **Connections->Add new Connection** en el menú de Grafana.

![alt text](../images/10/5.png)

4. Busca **Prometheus**.

![alt text](../images/10/6.png)

5. Ahora, selecciona **Add new data source**.

6. Crea el datasource con la siguiente información:

- Name: **prometheus**
- Prometheus server URL: **http://prometheus:9090**

- Todo lo demas se deja por default.

![alt text](../images/10/7.png)

7. Almacenar el datasource navegando hasta el final de las opciones de Prometheus y sólo pulsa **Save & test**.

![alt text](../images/10/8.png)

8. Ahora, en el menú de Grafana, selecciona **Dashboards**.

![alt text](../images/10/9.png)

9. En **Dashboards** escoge **New->Import**.

![alt text](../images/10/10.png)

10. Selecciona **Upload dashboard JSON file** y elige el archivo dashboard.json que tenemos en nuestra carpeta **prometheus y grafana**. 


![alt text](../images/10/11.png)

11. Selecciona el datasource **prometheus**.

12. Si hemos realizado correctamente las instrucciones deberíamos de observar el siguiente dashboard:


![alt text](../images/10/12.png)


## Resultado esperado

Si hemos llegado hasta aquí, el alumno ha implementado correctamente un dashboard de monitoreo para su microservicio cliente. 

![alt text](../images/10/12.png)


