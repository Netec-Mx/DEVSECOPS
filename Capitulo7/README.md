# Práctica 6. Hardening básico en Ubuntu:20.04
El alumno aprenderá cómo se asegura un servidor de Ubuntu usando un contenedor de Docker como servidor de prueba. 

## Objetivos de la práctica:
- Crear un contenedor docker con Ubuntu.
- Aprender los diferentes tipos de seguridad que se le puede dar a un servidor.

## Duración aproximada:
- 30 minutos.

---

<div style="width: 400px;">
        <table width="50%">
            <tr>
                <td style="text-align: center;">
                    <a href="../Capitulo6/"><img src="../images/anterior.png" width="40px"></a>
                    <br>anterior
                </td>
                <td style="text-align: center;">
                   <a href="../README.md">Lista Laboratorios</a>
                </td>
<td style="text-align: center;">
                    <a href="../Capitulo8/"><img src="../images/siguiente.png" width="40px"></a>
                    <br>siguiente
                </td>
            </tr>
        </table>
</div>

---


## Diagrama

![diagrama](../images/6/diagrama.png)

## Instrucciones
Esta práctica se divide en las siguientes secciones:

- **[Crear contenedor Ubuntu Server](#crear-un-contenedor-ubuntu-return)**

- **[Crear y ejecutar hardening script](#crear-y-ejecutar-hardening-script-return)**

## Crear un contenedor Ubuntu [return](#instrucciones)
1. Abre una terminal. 
2. Ejecuta el siguiente comando en la terminal: 

```bash
 docker run -it --name ubuntu_server --entrypoint /bin/bash ubuntu:20.04
```

> **NOTA:** Este comando creara un contenedor con la imagen de Ubuntu:20.04 ejecutandose, al terminar de ejecutarse el comando nos abrira una nueva terminal. 

![alt text](../images/6/1.png)


## Crear y ejecutar hardening script [return](#instrucciones)

1. Con la terminal del contenedor abierta, dirígete a la ruta **home.**

```bash
cd /home
```

2. En la ruta home ejecuta los siguientes comandos:

```bash
apt-get update
apt-get install nano
```

> **NOTA:** Por defecto, en Docker no se instala un editor de archivos así que instalaremos **nano** para la edición y creación del script. 

3. Crea una archivo llamado **hardening.sh** con el siguiente comando: 

```bash
nano hardening.sh
```

![alt text](../images/6/2.png)

4. Añade en el archivo el siguiente código de bash, que incorpora algunas fases de seguridad: 

- Actualiza el sistema.
- Instala seguridad básica (fail2ban, auditd).
- Configura actualizaciones automáticas.
- Configura o omite firewall según sea contenedor o no.
- Endurece SSH.
- Desactiva IPv6.

```bash
#!/bin/bash




# Comprobar si es root
if [ "$(id -u)" -ne 0 ]; then
    echo "Este script debe ejecutarse como root. Usa 'sudo ./harden_ubuntu.sh'"
    exit 1
fi

# Detectar si estamos en un contenedor
if [ -f /.dockerenv ] || grep -qa container=lxc /proc/1/environ; then
    echo "⚠️  Contenedor detectado: Se omitirá configuración de firewall."
    SKIP_FIREWALL=1
else
    SKIP_FIREWALL=0
fi

echo "===== ACTUALIZANDO EL SISTEMA ====="
apt update && apt upgrade -y
apt install -y unattended-upgrades fail2ban auditd

echo "===== CONFIGURANDO ACTUALIZACIONES AUTOMÁTICAS ====="
/bin/bash -c 'echo "Unattended-Upgrade::Automatic-Reboot \"true\";" > /etc/apt/apt.conf.d/50unattended-upgrades'

if [ "$SKIP_FIREWALL" -eq 0 ]; then
    echo "===== CONFIGURANDO UFW (FIREWALL) ====="
    apt install -y ufw
    ufw default deny incoming
    ufw default allow outgoing
    ufw allow 2222/tcp    # Puerto para SSH
    ufw allow 80/tcp      # HTTP
    ufw allow 443/tcp     # HTTPS
    ufw --force enable
else
    echo "🚫 Firewall omitido (contenedor detectado)"
fi

echo "===== CONFIGURANDO SSHD ====="
# Respaldar sshd_config
cp /etc/ssh/sshd_config /etc/ssh/sshd_config.bak

sed -i 's/#Port 22/Port 2222/' /etc/ssh/sshd_config
sed -i 's/PermitRootLogin yes/PermitRootLogin no/' /etc/ssh/sshd_config
sed -i 's/#PasswordAuthentication yes/PasswordAuthentication no/' /etc/ssh/sshd_config

# Solo reiniciar ssh si no estamos en contenedor (en contenedor a veces SSH ni está corriendo)
if [ "$SKIP_FIREWALL" -eq 0 ]; then
    systemctl reload sshd
fi

echo "===== CONFIGURANDO FAIL2BAN ====="
systemctl enable fail2ban
systemctl start fail2ban

echo "===== CONFIGURANDO AUDITD ====="
systemctl enable auditd
systemctl start auditd

echo "===== DESHABILITANDO IPv6 (opcional) ====="
echo "net.ipv6.conf.all.disable_ipv6 = 1" >> /etc/sysctl.conf
echo "net.ipv6.conf.default.disable_ipv6 = 1" >> /etc/sysctl.conf
sysctl -p

echo "===== HARDENING BÁSICO FINALIZADO ====="
echo "✅ Script ejecutado correctamente."
```

5. Le damos permisos de ejecución.

```bash
chmod +x hardening.sh
```

6. Valida que tenga los permisos de ejecución. 

![alt text](../images/6/3.png)

7. Ejecuta el script con el comando. 

```bash
. hardening.sh
```

![alt text](../images/6/4.png)

> **NOTA:** Algunas herramientas no se ejecutan correctamente porque nos encontramos en un contenedor y por eso no tenemos control completo del sistema operativo. 

> **IMPORTANTE:** Este es un ejemplo sencillo de cómo podríamos proteger un servidor de Ubuntu, aunque se le pueden añadir mas capas de seguridad en el caso de necesitarlo, pero la idea del hardening es minimizar el área de ataque de un servidor.


## Resultado esperado [Instrucciones](#instrucciones)

Se espera que el alumno haya podido ejecutar el hardening de un servidor de Ubuntu:20.04


![alt text](../images/6/4.png)
