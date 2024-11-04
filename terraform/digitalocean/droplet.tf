resource "digitalocean_droplet" "backend-v2" {
  image    = "ubuntu-22-04-x64"
  name     = "backend-v2"
  region   = "sgp1"
  size     = "s-1vcpu-2gb"
  vpc_uuid = digitalocean_vpc.shop-sgp-vpc.id
  ssh_keys = [data.digitalocean_ssh_key.vutiendat3601.id]
  depends_on = [ digitalocean_database_cluster.postgres-cluster ]

  provisioner "file" {
    source      = "./docker"
    destination = "/opt/shopsinhvien"
    connection {
      type        = "ssh"
      user        = "root"
      private_key = file("~/.ssh/vutiendat3601")
      host        = self.ipv4_address
    }
    when = create
  }
  provisioner "remote-exec" {
    when = create
    inline = [
        <<EOF
          echo "ALTER DATABASE ${digitalocean_database_db.shop.name} OWNER TO ${digitalocean_database_user.shop.name};" >> grant-postgres-shop-permisison.sql
          echo "GRANT ALL ON DATABASE ${digitalocean_database_db.shop.name} TO ${digitalocean_database_user.shop.name};" >> grant-postgres-shop-permisison.sql
        EOF
      ,
      # "PGPASSWORD=${digitalocean_database_cluster.postgres-cluster.password} psql -U ${digitalocean_database_cluster.postgres-cluster.user} -h ${digitalocean_database_cluster.postgres-cluster.host} -p ${digitalocean_database_cluster.postgres-cluster.port} -d ${digitalocean_database_db.shop.name} --set=sslmode=require < grant-postgres-shop-permisison.sql",
      "echo SPRING_DATASOURCE_URL=jdbc:postgresql://${digitalocean_database_cluster.postgres-cluster.host}:${digitalocean_database_cluster.postgres-cluster.port}/shop >> /opt/shopsinhvien/backend-v2.env",
      "echo SPRING_DATASOURCE_USERNAME=${digitalocean_database_user.shop.name} >> /opt/shopsinhvien/backend-v2.env",
      "echo SPRING_DATASOURCE_PASSWORD=${digitalocean_database_user.shop.password} >> /opt/shopsinhvien/backend-v2.env"
    ]
    connection {
      type        = "ssh"
      user        = "root"
      private_key = file("~/.ssh/vutiendat3601")
      host        = self.ipv4_address
    }
  }
  user_data = <<-EOF
              #!/bin/bash
              echo "deb http://apt.postgresql.org/pub/repos/apt $(lsb_release -cs)-pgdg main" > /etc/apt/sources.list.d/pgdg.list
              curl -fsSL https://www.postgresql.org/media/keys/ACCC4CF8.asc | gpg --dearmor -o /etc/apt/trusted.gpg.d/postgresql.gpg
              apt-get update && apt-get upgrade -y
              apt-get install git postgresql-client -y
              git clone https://github.com/vutiendat3601/cli.git
              /cli/docker_install-ubuntu.sh
              /cli/certbot_ubuntu-install.sh
              EOF
}
