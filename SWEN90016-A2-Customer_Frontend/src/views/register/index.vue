<template>
  <div class="login-container">
    <el-form
      ref="registerForm"
      :rules="registerRules"
      :model="registerForm"
      class="login-form"
      auto-complete="on">
      <h1 class="title">Register Account</h1>
      <h2>Personal Information</h2>
      <br/>
      <el-form-item prop="name">
        <el-input
          size="large"
          placeholder="Name"
          prefix-icon="el-icon-s-custom"
          v-model="registerForm.name">
        </el-input>
      </el-form-item>
      <el-form-item prop="email">
        <el-input
          size="large"
          placeholder="Email/Username"
          prefix-icon="el-icon-s-custom"
          type = "email"
          v-model="registerForm.email">
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          size="large"
          placeholder="Password"
          type="password"
          prefix-icon="el-icon-lock"
          v-model="registerForm.password"
          @keyup.enter.native="handleRegister">
        </el-input>
      </el-form-item>
      <el-form-item prop="confirmPassword">
        <el-input
          size="large"
          placeholder="Password"
          type="password"
          prefix-icon="el-icon-lock"
          v-model="registerForm.confirmPassword"
          @keyup.enter.native="handleRegister">
        </el-input>
      </el-form-item>
      <el-form-item prop="address">
        <el-input
          size="large"
          placeholder="Address"
          prefix-icon="el-icon-s-custom"
          v-model="registerForm.address">
        </el-input>
      </el-form-item>
      <el-form-item prop="phone">
        <el-input
          size="large"
          placeholder="Phone"
          prefix-icon="el-icon-s-custom"
          v-model.number="registerForm.phone">
        </el-input>
      </el-form-item>
      <el-form-item prop="comment">
        <el-input
          size="large"
          placeholder="Comment"
          prefix-icon="el-icon-s-custom"
          v-model="registerForm.comment">
        </el-input>
      </el-form-item>
      <h2>Biller Information</h2>
      <br/>
      <el-form-item prop="billerName">
        <el-input
          size="large"
          placeholder="Biller Name"
          prefix-icon="el-icon-s-custom"
          v-model="registerForm.billerName">
        </el-input>
      </el-form-item>
      <el-form-item prop="billerEmail">
        <el-input
          size="large"
          placeholder="Biller Email"
          prefix-icon="el-icon-s-custom"
          v-model="registerForm.billerEmail"
          type = "email">
        </el-input>
      </el-form-item>
      <el-button :loading="loading" type="primary" size="large" class="register-btn" @click.native.prevent="handleRegister">
        Register
      </el-button>
    </el-form>
  </div>
</template>

<script>

  export default {
    name: 'register',
    data () {
      let passwordsMatch = (rule, value, callback) => {
        if (value !== this.registerForm.password) {
          return callback(new Error('Passwords do not match'))
        } else {
          return callback()
        }
      }
      return {
        registerForm: {
          name: '',
          email: '',
          password: '',
          confirmPassword: '',
          phone: '',
          address: '',
          comment: '',
          billerName: '',
          billerEmail: ''
        },
        registerRules: {
          name: [
            { required: true, message: 'Name cannot be empty', trigger: 'blur' }
          ],
          email: [
            { required: true, message: 'Email cannot be empty', trigger: 'blur' },
            { type: 'email', message: 'Must be an email', trigger: 'blur' }
          ],
          password: [
            { required: true, message: 'Password cannot be empty', trigger: 'blur' }
          ],
          confirmPassword: [
            { required: true, message: 'Password is required', trigger: 'blur' },
            { validator: passwordsMatch, trigger: 'blur' }
          ],
          phone: [
            { required: true, message: 'Phone cannot be empty', trigger: 'blur' },
            { type: 'number', message: 'Must be a phone number', trigger: 'blur' }
          ],
          address: [
            { required: true, message: 'Address cannot be empty', trigger: 'blur' }
          ],
          comment: [
            { required: true, message: 'Address cannot be empty', trigger: 'blur' }
          ],
          billerName: [
            { required: true, message: 'Comment cannot be empty', trigger: 'blur' }
          ],
          billerEmail: [
            { required: true, message: 'Biller email cannot be empty', trigger: 'blur' },
            { type: 'email', message: 'Must be an email', trigger: 'blur' }
          ]
        },
        loading: false,
        showDialog: false
      }
    },
    methods: {
      handleRegister () {
        this.$refs.registerForm.validate(validate => {
          if (validate) {
            this.loading = true
            this.$store.dispatch('register', this.registerForm).then(() => {
              this.$router.push('/project/service/introduceService')
              this.loading = false
            }, () => {
              this.loading = false
            })
          } else {
            return false
          }
        })
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
      .register-btn {
        width: 100%;
      }
    }
  }
</style>
