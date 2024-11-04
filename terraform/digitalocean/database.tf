# PostgreSQL cluster
resource "digitalocean_database_cluster" "postgres-cluster" {
  name                 = "postgres-cluster"
  engine               = "pg"
  version              = "16"
  size                 = "db-s-1vcpu-1gb"
  region               = "sgp1"
  node_count           = 1
}

# PostgreSQL database
resource "digitalocean_database_db" "shop" {
  cluster_id = digitalocean_database_cluster.postgres-cluster.id
  name       = "shop"
}

# PostgreSQL user
resource "digitalocean_database_user" "shop" {
  depends_on = [ digitalocean_database_db.shop ]
  cluster_id = digitalocean_database_cluster.postgres-cluster.id
  name       = "shop"
  # provisioner "local-exec" {
  #   command = "PGPASSWORD= "
  # }
}

resource "digitalocean_database_firewall" "postgres-cluster-firewall" {
  cluster_id = digitalocean_database_cluster.postgres-cluster.id

  rule {
    type  = "droplet"
    value = digitalocean_droplet.backend-v2.id
  }
}
