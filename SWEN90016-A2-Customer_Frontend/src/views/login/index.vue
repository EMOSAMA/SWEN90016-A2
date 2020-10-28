<template>
  <div class="login-container">
    <el-form
      ref="loginForm"
      :rules="loginRules"
      :model="loginForm"
      class="login-form"
      auto-complete="on">
      <h1 class="title">Online Appointment System</h1>
      <el-form-item prop="Username">
        <el-input
          size="large"
          placeholder="Username/Email"
          prefix-icon="el-icon-s-custom"
          v-model="loginForm.userName">
        </el-input>
      </el-form-item>
      <el-form-item prop="Password">
        <el-input
          size="large"
          placeholder="Password"
          type="password"
          prefix-icon="el-icon-lock"
          v-model="loginForm.password"
          @keyup.enter.native="handleLogin">
        </el-input>
      </el-form-item>
      <el-button :loading="loading" type="primary" size="large" class="login-btn" @click.native.prevent="handleLogin">
        Login
      </el-button>
      <br/>
      <br/>
      <el-button type="secondary" size="large" class="register-btn" @click.native.prevent="handleRegister">
        Register
      </el-button>
    </el-form>
  </div>
</template>

<script>

  export default {
    name: 'login',
    data () {
      return {
        loginForm: {
          userName: '',
          password: ''
        },
        loginRules: {
          userName: [
            { required: true, message: 'email cannot be null', trigger: 'blur' }
          ],
          password: [
            { required: true, message: 'password cannot be null', trigger: 'blur' }
          ]
        },
        loading: false,
        showDialog: false
      }
    },
    methods: {
      handleLogin () {
        this.$refs.loginForm.validate(validate => {
          if (validate) {
            this.loading = true
            this.$store.dispatch('login', this.loginForm).then(() => {
              this.$router.push('/project/service/introduceService')
              this.loading = false
            }, () => {
              this.loading = false
            })
          } else {
            return false
          }
        })
      },
      handleRegister () {
        this.$router.push('/register')
      }
    },
    mounted () {
    }
  }
</script>

<style lang="scss">
  /*重置element*/
  $bg: #283443;
  $light_gray: #fff;
  $cursor: #fff;

  @supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
    .login-container .el-input input {
      color: $cursor;
    }
  }

  .login-container {
    .el-input {
      input {
        background: transparent;
        border: none;

        &:-webkit-autofill {
          box-shadow: 0 0 0px 1000px $bg inset !important;
          -webkit-text-fill-color: $cursor !important;
        }
      }
    }

    .el-form-item {
      border: 1px solid rgba(255, 255, 255, 0.1);
      background: rgba(0, 0, 0, 0.1);
      border-radius: 5px;
      color: #454545;
    }
  }
</style>

<style lang="scss" scoped>
  $bg: #2d3a4b;
  $dark_gray: #889aa4;
  $light_gray: #eee;
  .login-container {
    min-height: 100%;
    width: 100%;
    background-color: $bg;

    .login-form {
      position: relative;
      width: 520px;
      max-width: 100%;
      padding: 160px 35px 0;
      margin: 0 auto;
      overflow: hidden;

      .title {
        margin-bottom: 30px;
        text-align: center;
        color: #fff;
      }

      .login-btn {
        width: 100%;
      }
      .register-btn {
        width: 100%;
      }
    }
  }
</style>
