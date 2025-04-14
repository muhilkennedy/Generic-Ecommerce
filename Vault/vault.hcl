storage "consul" {
  address = "127.0.0.1:8500" # Consul address
  path    = "vault/"
}

listener "tcp" {
  address     = "0.0.0.0:8200"  # Vault listens on all IPs
  cluster_address = "0.0.0.0:8201"
  tls_disable = 0
  tls_cert_file = "/etc/vault.d/vault-cert.pem"
  tls_key_file  = "/etc/vault.d/vault-key.pem"
}

api_addr = "https://<vault-server>:8200"
cluster_addr = "https://<vault-server>:8201"
ui = true  # Enable Vault UI

seal "awskms" {
  region = "us-east-1"  
  access_key = "<aws-access-key>"
  secret_key = "<aws-secret-key>"
  kms_key_id = "<kms-key-id>"
}

audit "file" {
  path = "/var/log/vault_audit.log"
}
