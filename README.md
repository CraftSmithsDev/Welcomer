# Welcomer Plugin

Welcome to the **Welcomer** plugin! This plugin allows you to customize join and leave messages, as well as display a **custom welcome message** (like EssentialsX's `motd.txt`) to players when they join the server. It supports both **MiniMessage** (modern formatting) and **Legacy** (traditional color codes) for message customization.

---

## Features
- **Custom Join and Leave Messages**:
  - Override the default join and leave messages with your own custom messages.
  - Supports placeholders like `%player%` for the player's name.

- **Custom Welcome Message (MOTD)**:
  - Display a multi-line welcome message to players when they join.
  - Similar to EssentialsX's `motd.txt` feature.

- **Color Code Systems**:
  - Supports **MiniMessage** (modern formatting) and **Legacy** (traditional color codes like `&7`, `&a`, etc.).
  - Choose your preferred system in the `config.yml`.

- **Reload Command**:
  - Use `/wr` to reload the configuration file without restarting the server.

---

## Installation
1. Download the latest `.jar` file from the [Releases](https://github.com/your-repo/Welcomer/releases) page.
2. Place the `.jar` file into your server's `plugins` folder.
3. Restart the server to generate the default configuration file.

---

## Configuration
The plugin generates a `config.yml` file in the `plugins/Welcomer` folder. Here’s an example configuration:

```yaml
# Welcomer Configuration

# Color Code System (MINIMESSAGE or LEGACY)
colorcode-system: MINIMESSAGE

# Join and Leave Messages
join-message: "<gradient:red:blue>Welcome, %player%!</gradient>"
leave-message: "&8[&c-&8] &7%player%"

# Custom Welcome Message (auto generate broken)
motd: false
welcome-motd:
  - "&o                                               "
  - "&7 Welcome %player% to the server"
  - ""
  - "&7 Enjoy your stay!"
  - "&7 Rules: Be nice and have fun!"
```
