resource "digitalocean_project" "shop" {
  name        = "shop"
  description = "A project to deploy vutiendat3601 shop"
  purpose     = "Web Application"
  environment = "Development"
  resources   = [digitalocean_droplet.backend-v2.urn, digitalocean_database_cluster.postgres-cluster.urn]
}
