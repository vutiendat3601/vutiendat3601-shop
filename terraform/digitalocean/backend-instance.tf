
resource "digitalocean_droplet" "backend-v2" {
  image  = "ubuntu-20-04-x64"
  name   = "backend-v2"
  region = "sgp2"
  size   = "s-1vcpu-1gb"
}
