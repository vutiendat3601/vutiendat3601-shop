resource "digitalocean_database_cluster" "postgres-cluster" {
  name       = "postgres-cluster"
  engine     = "pg"
  version    = "16"
  size       = "db-s-1vcpu-1gb"
  region     = "sgp1"
  node_count = 1
}
